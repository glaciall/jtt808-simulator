package cn.org.hentai.simulator.state;

import cn.org.hentai.simulator.entity.Task;
import cn.org.hentai.simulator.manager.ThreadManager;
import cn.org.hentai.simulator.util.BeanUtils;
import cn.org.hentai.simulator.web.entity.ScheduleTask;
import cn.org.hentai.simulator.web.service.ScheduleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by houcheng on 2018/11/13.
 * 初始化
 */
public class ST10Init extends IState<Task>
{
    private static Logger logger = LoggerFactory.getLogger(ST10Init.class);

    @Override
    public String name()
    {
        return "初始化";
    }

    @Override
    public Result handle(Task task)
    {
        // 如果开始时间还没有到，就继续等待
        if (System.currentTimeMillis() < task.getStartTime().getTime()) return Result.none;
        logger.debug("行程计划：" + task.getScheduleTaskId() + "已启动...");

        // 先更新计划任务状态信息
        ScheduleTaskService scheduleTaskService = null;
        try
        {
            // 更新计划任务的执行信息
            scheduleTaskService = BeanUtils.create(ScheduleTaskService.class);
            ScheduleTask plan = scheduleTaskService.getById(task.getScheduleTaskId());
            if (plan != null)
            {
                plan.setLastDriveTime(task.getStartTime());
                plan.setDriveCount((plan.getDriveCount() == null ? 0 : plan.getDriveCount()) + 1);
                scheduleTaskService.update(plan);
            }
        }
        catch(Exception e)
        {
            logger.error("update-schedule-task-info", e);
        }
        finally
        {
            try { BeanUtils.destroy(scheduleTaskService); } catch(Exception e) { }
        }

        // 行程任务初始化，主要是生成线路的新轨迹信息、事件信息等。
        ThreadManager.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    task.init();
                    task.getState().notify(Result.success);
                }
                catch(Exception e)
                {
                    task.getState().notify(Result.failure);
                    logger.error("task-init-worker", e);
                }
            }
        });
        return Result.wait;
    }

    @Override
    public Class<? extends IState> next()
    {
        return ST20Connect.class;
    }

    @Override
    public Class<? extends IState> error()
    {
        return ST99Finality.class;
    }
}
