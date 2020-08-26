package cn.org.hentai.simulator.task;

import cn.org.hentai.simulator.entity.DrivePlan;
import cn.org.hentai.simulator.entity.Point;
import cn.org.hentai.simulator.entity.TaskInfo;
import cn.org.hentai.simulator.manager.RouteManager;
import cn.org.hentai.simulator.task.log.Log;
import cn.org.hentai.simulator.task.runner.Executable;
import cn.org.hentai.simulator.web.vo.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 行程任务管理器
 */
public final class TaskManager
{
    static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    Object lock;
    // TODO: map的value是无序的，不好做分页，得想个办法
    ConcurrentHashMap<Long, AbstractDriveTask> tasks;
    AtomicLong sequence;
    AtomicLong index;

    static final Comparator<AbstractDriveTask> SORT_COMPARATOR = new Comparator<AbstractDriveTask>()
    {
        @Override
        public int compare(AbstractDriveTask o1, AbstractDriveTask o2)
        {
            long x = o1.getId() - o2.getId();
            if (x > 0) return 1;
            else if (x == 0) return 0;
            else return -1;
        }
    };

    private TaskManager()
    {
        this.lock = new Object();
        this.tasks = new ConcurrentHashMap<Long, AbstractDriveTask>();

        this.index = new AtomicLong(0L);
        this.sequence = new AtomicLong(0L);
    }

    /**
     * 按给定的参数集，开启任务
     * @param params
     * @param routeId
     */
    public void run(Map params, Long routeId)
    {
        // TODO: 需要检查一下是不是有冲突（终端ID及SIM卡号不能重复）
        DrivePlan plan = RouteManager.getInstance().generate(routeId, new Date());

        AbstractDriveTask task = new SimpleDriveTask(this.sequence.addAndGet(1L), routeId);
        task.init(params, plan);
        task.startup();

        tasks.put(task.getId(), task);
    }

    public long nextIndex()
    {
        return this.index.addAndGet(1L);
    }

    // 分页查找，用于列表显示运行中的行程任务状态
    public Page<TaskInfo> find(int pageIndex, int pageSize)
    {
        AbstractDriveTask[] list = tasks.values().toArray(new AbstractDriveTask[0]);
        Arrays.sort(list, SORT_COMPARATOR);
        List<TaskInfo> results = new ArrayList<TaskInfo>(pageSize);
        for (int k = 0, i = Math.max((pageIndex - 1) * pageSize, 0); k < pageSize && i < list.length; i++, k++)
        {
            results.add(list[i].getInfo());
        }
        Page<TaskInfo> page = new Page(pageIndex, pageSize);
        page.setList(results);
        page.setRecordCount(list.length);
        return page;
    }

    // 获取timeAfter时间之后的任务日志
    public List<Log> getLogsById(Long id, long timeAfter)
    {
        AbstractDriveTask task = tasks.get(id);
        if (task != null) return task.getLogs(timeAfter);
        else return null;
    }

    public TaskInfo getById(Long id)
    {
        TaskInfo info = null;
        AbstractDriveTask task = tasks.get(id);
        if (task == null) return null;
        else return task.getInfo();
    }

    // 获取当前位置信息
    public Point getCurrentPositionById(Long id)
    {
        AbstractDriveTask task = tasks.get(id);
        if (task == null) return null;
        else return task.getCurrentPosition();
    }

    // 修改车辆状态标志位
    public void setStateFlagById(Long id, int index, boolean on)
    {
        AbstractDriveTask task = tasks.get(id);
        if (task != null) task.setStateFlag(index, on);
    }

    // 修改报警状态标志位
    public void setWarningFlagById(Long id, int index, boolean on)
    {
        AbstractDriveTask task = tasks.get(id);
        if (task != null) task.setWarningFlag(index, on);
    }

    // 任务终止
    // TODO: 什么时候把任务从map里删除掉好呢？
    public void terminate(Long id)
    {
        AbstractDriveTask task = tasks.get(id);
        if (task == null) throw new RuntimeException("无此任务或任务已终止");
        task.execute(new Executable()
        {
            @Override
            public void execute(AbstractDriveTask driveTask)
            {
                driveTask.terminate();
            }
        });
    }

    static final TaskManager instance = new TaskManager();
    public static void init()
    {
        // ...
    }
    public static TaskManager getInstance()
    {
        return instance;
    }
}