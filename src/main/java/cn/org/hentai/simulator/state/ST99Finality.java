package cn.org.hentai.simulator.state;

import cn.org.hentai.simulator.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by houcheng on 2018/11/13.
 * 行程结束
 */
public class ST99Finality extends IState<Task>
{

    private static Logger logger = LoggerFactory.getLogger(ST99Finality.class);

    @Override
    public String name()
    {
        return "闭幕";
    }

    private long startTime = 0L;

    @Override
    public void recycle()
    {
        super.recycle();
        startTime = System.currentTimeMillis() + 1000L * 60 * 5;
    }

    @Override
    public Result handle(Task task)
    {
        // 等待全部的消息包发送完毕
        if (startTime > System.currentTimeMillis()) return Result.none;

        logger.debug(String.format("行程计划: %d 已结束...", task.getScheduleTaskId()));

        // 资源释放
        try
        {
            task.release();
        }
        catch(Exception ex)
        {
            logger.error("", ex);
        }
        return Result.finality;
    }

    @Override
    public Class<? extends IState> next()
    {
        return null;
    }

    @Override
    public Class<? extends IState> error()
    {
        return null;
    }
}
