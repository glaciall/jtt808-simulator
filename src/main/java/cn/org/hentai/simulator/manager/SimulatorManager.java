package cn.org.hentai.simulator.manager;

import cn.org.hentai.simulator.entity.*;
import cn.org.hentai.simulator.state.IState;
import cn.org.hentai.simulator.web.vo.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by houcheng on 2018/11/23.
 * 车机模拟器管理器
 */
public final class SimulatorManager
{
    private static Logger logger = LoggerFactory.getLogger(SimulatorManager.class);

    public static enum Command
    {
        start, stop, pause, remove
    };

    static final int WORKER_COUNT = 8;
    static SimulatorManager instance = null;
    LinkedList<Task> tasks = new LinkedList<Task>();                                                                // 全部运行中的行程任务
    SimulatorWorker[] workers = new SimulatorWorker[WORKER_COUNT];

    private static class SimulatorWorker extends Thread
    {
        ConcurrentHashMap<Long, Command> taskCommands = new ConcurrentHashMap<Long, Command>();                     // 车机模拟器指令
        LinkedList<Task> readyTasks = new LinkedList<Task>();                                                           // 待开始运行的行程任务
        LinkedList<Integer> readyToRemoveTasks = new LinkedList<Integer>();                                             // 待从队列中删除的行程任务

        LinkedList<Task> tasks = new LinkedList<Task>();                                                                // 当前线程运行中的行程任务
        long lastActiveTime = 0L;               // 最后循环执行时间
        long lastLoopSpend = 0L;                // 最近一次循环耗时
        Map<String, Integer> taskStatistics = new HashMap();

        // 添加新的任务
        public void add(Task task)
        {
            synchronized (readyTasks)
            {
                readyTasks.addLast(task);
            }
        }

        // 通知指定车辆的行程：开始/继续运行、停止、暂停
        public void notify(Long id, Command cmd)
        {
            taskCommands.put(id, cmd);
        }

        // 启动线程执行器
        @Override
        public void run()
        {
            while (!this.isInterrupted())
            {
                try
                {
                    renewActiveTime();

                    // 遍历行程任务，挨个干活
                    Map<String, Integer> stateCounting = new HashMap();
                    long beginTime = System.currentTimeMillis();
                    for (int i = 0; i < tasks.size(); i++)
                    {
                        Task task = tasks.get(i);
                        boolean running = task.run();

                        IState state = task.getState();
                        if (state != null)
                        {
                            Integer count = stateCounting.get(state.name());
                            if (null == count) count = 0;
                            count += 1;
                            stateCounting.put(state.name(), count);
                        }

                        if (running == false)
                        {
                            readyToRemoveTasks.add(i);
                            continue;
                        }

                        XVehicleDevice vd = task.getVehicleDevice();
                        if (null == vd) continue;
                        Command cmd = taskCommands.remove(task.getId());
                        if (null == cmd) continue;

                        // 如果已经完成，或是要求直接移除
                        if (cmd == Command.remove || cmd == Command.stop)
                        {
                            // TODO: 这里没有经过最后一步，感觉不大稳妥
                            readyToRemoveTasks.add(i);
                        }

                        // 这也把task移出到别的队列里去吧，省事
                        // TODO: 如果要继续运行

                        // TODO: 如果要暂停运行
                    }
                    setTaskStatistics(stateCounting);
                    setLastLoopSpend(System.currentTimeMillis() - beginTime);

                    // 看看有没有新加进来的行程任务
                    synchronized (readyTasks)
                    {
                        for (Task task : readyTasks)
                        {
                            tasks.addLast(task);
                        }
                        readyTasks.clear();
                    }

                    // 删除任务
                    for (int i = readyToRemoveTasks.size() - 1; i >= 0; i--)
                    {
                        int index = readyToRemoveTasks.get(i);
                        Task removedTask = tasks.remove(index);
                        if (removedTask != null) instance.tasks.remove();
                    }
                    readyToRemoveTasks.clear();

                    // 如果车辆实在是太少，那就停一小会儿
                    trySleep();
                }
                catch(Exception e)
                {
                    logger.error("simulator-eventloop-error", e);
                }
            }
            logger.info("simulator[{}] started...", getName());
        }

        private void trySleep()
        {
            if (System.currentTimeMillis() - lastActiveTime > 500) return;
            try { Thread.sleep(100); } catch(Exception e) { }
        }

        private void renewActiveTime()
        {
            this.lastActiveTime = System.currentTimeMillis();
        }

        // 返回最近一次轮循的时间
        public long getLastActiveTime()
        {
            return this.lastActiveTime;
        }

        public long getLastLoopSpend()
        {
            return lastLoopSpend;
        }

        public SimulatorWorker setLastLoopSpend(long lastLoopSpend)
        {
            this.lastLoopSpend = lastLoopSpend;
            return this;
        }

        public Map<String, Integer> getTaskStatistics()
        {
            return taskStatistics;
        }

        public SimulatorWorker setTaskStatistics(Map<String, Integer> taskStatistics)
        {
            this.taskStatistics = taskStatistics;
            return this;
        }
    }

    // 添加新的任务
    public void add(Task task)
    {
        int idx = (task.getId().hashCode() & 0x7fffffff) % WORKER_COUNT;
        workers[idx].add(task);
        tasks.add(task);
    }

    // 通知指定车辆的行程：开始/继续运行、停止、暂停
    public void notify(Long id, Command cmd)
    {
        int idx = (id.hashCode() & 0x7fffffff) % WORKER_COUNT;
        workers[idx].notify(id, cmd);
    }

    // 返回正在运行中的行程计划总数
    public int getRunningCount()
    {
        return this.tasks.size();
    }

    public Map<String, Integer> getTaskStatistics()
    {
        Map<String, Integer> stat = new HashMap();
        for (SimulatorWorker worker : workers)
        {
            Map<String, Integer> item = worker.getTaskStatistics();
            Iterator<String> itr = item.keySet().iterator();
            while (itr.hasNext())
            {
                String key = itr.next();
                Integer val = item.get(key);
                if (stat.containsKey(key)) val = val + stat.get(key);
                stat.put(key, val);
            }
        }
        return stat;
    }

    public long[] getLastLoopSpend()
    {
        long[] time = new long[WORKER_COUNT];
        int i = 0;
        for (SimulatorWorker worker : workers)
        {
            time[i++] = worker.getLastLoopSpend();
        }
        return time;
    }

    public long[] getLastActiveTime()
    {
        long[] time = new long[WORKER_COUNT];
        int i = 0;
        for (SimulatorWorker worker : workers)
        {
            time[i++] = worker.getLastActiveTime();
        }
        return time;
    }

    public TaskInfo getTaskById(Long id)
    {
        long now = System.currentTimeMillis();
        for (Task task : tasks)
        {
            if (task.getId().equals(id))
            {
                TaskInfo info = new TaskInfo();
                XDriver driver = task.getDriver();
                if (driver != null)
                {
                    info.setDriverId(driver.getId());
                    info.setDriverName(driver.getName());
                }
                info.setVehicleDeviceSN(task.getVehicleDevice().getDeviceSn());
                info.setVehicleId(task.getVehicleId());
                info.setPoints(task.getTracks());
                Point point = task.getCurrentPosition();
                if (point != null)
                {
                    info.setLongitude(point.getLongitude());
                    info.setLatitude(point.getLatitude());
                }
                info.setAlive(now - task.getLastActiveTime() < 1000);
                info.setRouteId(task.getRouteId());
                info.setStartTime(task.getStartTime());
                info.setId(task.getId());
                info.setState(task.getState().name());

                return info;
            }
        }
        return null;
    }

    public Page<TaskInfo> find(Long routeId, Long driverId, String deviceSN, int pageIndex, int pageSize)
    {
        if (pageIndex <= 0) pageIndex = 1;
        if (pageSize <= 0) pageSize = 50;
        Page<TaskInfo> page = new Page<TaskInfo>(pageIndex, pageSize);
        List<TaskInfo> list = new LinkedList();

        LinkedList<Task> taskList = new LinkedList();
        synchronized (tasks)
        {
            for (Task task : tasks)
            {
                if (routeId != null && task.getRouteId().equals(routeId) == false) continue;
                XDriver driver = task.getDriver();
                if (driverId != null && (driver == null || driverId.equals(driver.getId()) == false)) continue;
                if (StringUtils.isEmpty(deviceSN) == false && deviceSN.equals(task.getVehicleDevice().getDeviceSn()) == false)
                    continue;
                taskList.add(task);
            }

            long now = System.currentTimeMillis();
            for (int i = (pageIndex - 1) * pageSize, k = 0; i < taskList.size() && k < pageSize; i++, k++)
            {
                Task task = taskList.get(i);
                TaskInfo info = new TaskInfo();
                XDriver driver = task.getDriver();
                if (driver != null)
                {
                    info.setDriverId(driver.getId());
                    info.setDriverName(driver.getName());
                }
                if (task.getVehicleDevice() != null) info.setVehicleDeviceSN(task.getVehicleDevice().getDeviceSn());
                info.setVehicleId(task.getVehicleId());
                Point point = task.getCurrentPosition();
                if (point != null)
                {
                    info.setLongitude(point.getLongitude());
                    info.setLatitude(point.getLatitude());
                }
                info.setAlive(now - task.getLastActiveTime() < 1000);
                info.setRouteId(task.getRouteId());
                info.setStartTime(task.getStartTime());
                info.setId(task.getId());
                info.setState(task.getState().name());

                list.add(info);
            }
        }
        page.setList(list);
        page.setRecordCount(taskList.size());
        return page;
    }

    public synchronized static SimulatorManager getInstance()
    {
        if (instance == null) instance = new SimulatorManager();
        return instance;
    }

    // 初始化，做点什么还没有想好
    public void init()
    {
        for (int i = 0; i < WORKER_COUNT; i++)
        {
            workers[i] = new SimulatorWorker();
            workers[i].start();
        }
    }

    private SimulatorManager()
    {
        // ...
    }
}
