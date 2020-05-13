package cn.org.hentai.simulator.manager;

import cn.org.hentai.simulator.entity.Task;
import cn.org.hentai.simulator.util.BeanUtils;
import cn.org.hentai.simulator.util.Configs;
import cn.org.hentai.simulator.util.DateUtils;
import cn.org.hentai.simulator.util.SIMGenerator;
import cn.org.hentai.simulator.web.entity.ScheduleTask;
import cn.org.hentai.simulator.web.service.ScheduleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by houcheng on 2018/12/2.
 * 计划任务管理器，与数据库保持同步，生成行程计划
 */
public final class ScheduleTaskManager
{
    private static Logger logger = LoggerFactory.getLogger(ScheduleTaskManager.class);
    final long DAY = 1000L * 60 * 60 * 24;

    // 计划任务
    LinkedList<ScheduleTask> schedulePlans = new LinkedList();

    // 应用启动时，加载全部
    public void init()
    {
        ScheduleTaskService taskService = null;
        try
        {
            taskService = BeanUtils.create(ScheduleTaskService.class);
            for (ScheduleTask scheduleTask : taskService.find())
            {
                load(scheduleTask);
            }

            //load(taskService.getById(24L));
            //load(taskService.getById(23L));
        }
        catch(Exception ex)
        {
            logger.error("计划任务管理器初始化异常！", ex);
        }
        finally
        {
            try { BeanUtils.destroy(taskService); } catch(Exception e) { }
        }
    }

    public void start(Long scheduleTaskId)
    {
        try
        {
            load(scheduleTaskId);
            start();
        }
        catch(Exception ex)
        {
            logger.error("计划任务管理器初始化异常！", ex);
        }
    }

    // 单个加载
    public void load(ScheduleTask scheduleTask)
    {
        if (schedulePlans.size() > 0) remove(scheduleTask.getId());
        synchronized (schedulePlans)
        {
            schedulePlans.add(scheduleTask);
        }
    }

    public void load(Long scheduleTaskId)
    {
        ScheduleTaskService taskService = null;
        try
        {
            taskService = BeanUtils.create(ScheduleTaskService.class);
            load(taskService.getById(scheduleTaskId));
        }
        catch(Exception ex)
        {
            logger.error("", ex);
        }
        finally
        {
            try { BeanUtils.destroy(taskService); } catch(Exception e) { }
        }
    }

    // 删除
    public void remove(Long scheduleTaskId)
    {
        int i = -1;
        synchronized (schedulePlans)
        {
            for (ScheduleTask scheduleTask : schedulePlans)
            {
                i += 1;
                if (scheduleTask.getId().equals(scheduleTaskId))
                {
                    schedulePlans.remove(i);
                    break;
                }
            }
        }
    }

    // 启动任务
    private void start(ScheduleTask plan)
    {
        // 驾驶员
        // 车辆/车机

        try
        {
            logger.info(String.format("ready to start simulator: %d", plan.getId()));

            String fmt = "yyyy-MM-dd HH:mm:ss";
            String today = DateUtils.format(DateUtils.today());
            long timespan = (long)(Math.random() * (DateUtils.parse(today + " " + plan.getEndTime(), fmt).getTime() - DateUtils.parse(today + " " + plan.getFromTime(), fmt).getTime()));
            Date startTime = new Date(DateUtils.parse(today + " " + plan.getFromTime(), fmt).getTime() + timespan);
            // 更新计划任务的最后行驶时间，避免重复运行
            plan.setLastDriveTime(startTime);

            Long driverId = plan.getDriverId();

            // 064621811122
            String driverPhoto = null;

            // 添加到任务队列里去
            Task task = new Task(plan.getId(), plan.getRouteId(), plan.getVehicleId(), null, String.valueOf(SIMGenerator.get()), driverPhoto, startTime);
            SimulatorManager.getInstance().add(task);

            logger.debug(String.format("Simulator started: %d, %s, %s", plan.getId(), null, DateUtils.format(startTime, fmt)));
        }
        catch(Exception ex)
        {
            logger.error("", ex);
        }
    }

    // 启动
    public void start()
    {
        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    postTasks();
                }
                catch(Exception ex)
                {
                    logger.error("计划任务管理器启动异常！", ex);
                }
            }
        }, 0, 1000 * 60 * 5);
        logger.debug("计划任务管理器启动成功。");
    }

    private void postTasks()
    {
        LinkedList<Integer> readyToRemovePlans = new LinkedList();
        LinkedList<ScheduleTask> readyToStartPlans = new LinkedList();
        synchronized (schedulePlans)
        {
            long now = System.currentTimeMillis();
            for (ScheduleTask scheduleTask : schedulePlans)
            {
                // TODO: 是否已经不再需要运行了？是否达到运行次数上限
                // 是否需要运行？
                // 距离最后一次运行的时间是否
                int runCount = scheduleTask.getRunCount() == null ? 0 : scheduleTask.getRunCount();
                int drivedCount = scheduleTask.getDriveCount() == null ? 0 : scheduleTask.getDriveCount();

                if (scheduleTask.getLastDriveTime() == null || (scheduleTask.getLastDriveTime().getTime() + scheduleTask.getDaysInterval() * DAY < now))
                {
                    if (runCount > 0 && drivedCount < runCount)
                        readyToStartPlans.add(scheduleTask);
                }
            }

            for (int i = readyToRemovePlans.size() - 1; i >= 0; i--)
            {
                schedulePlans.remove(readyToRemovePlans.get(i));
            }
        }

        for (ScheduleTask scheduleTask : readyToStartPlans)
        {
            try
            {
                start(scheduleTask);
            }
            catch(Exception e)
            {
                logger.error("Error while startup schedule task: " + scheduleTask.getId(), e);
            }
        }
    }

    // 单例模式
    static ScheduleTaskManager instance = null;

    private ScheduleTaskManager() { }

    public static synchronized ScheduleTaskManager getInstance()
    {
        if (null == instance) instance = new ScheduleTaskManager();
        return instance;
    }
}
