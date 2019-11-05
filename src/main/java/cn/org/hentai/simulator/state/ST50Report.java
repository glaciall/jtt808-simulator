package cn.org.hentai.simulator.state;

import cn.org.hentai.simulator.entity.Point;
import cn.org.hentai.simulator.entity.Task;
import cn.org.hentai.simulator.entity.XEvent;
import cn.org.hentai.simulator.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by houcheng on 2018/11/13.
 * 事件上报
 */
public class ST50Report extends IState<Task>
{
    private static Logger logger = LoggerFactory.getLogger(ST50Report.class);

    @Override
    public String name()
    {
        return "位置上报";
    }

    static long xxooTime = DateUtils.parse("2019-03-01 11:55:00", "yyyy-MM-dd HH:mm:ss").getTime();

    @Override
    public Result handle(final Task task)
    {
        if (System.currentTimeMillis() < xxooTime) return Result.none;

        Point position = task.nextPosition();
        if (position == null)
        {
            return Result.success;
        }

        // 事件发生时间大于当前系统时间，不执行发送操作
        if (System.currentTimeMillis() < position.getReportTime())
        {
            return Result.none;
        }

        try
        {
            Point backPoint = task.sendTrack(position);
            task.setCurrentPosition(backPoint);

            final XEvent event = task.getPendingEvent(position.getReportTime());
            if (event != null)
            {
                task.sendEvent(event);
            }
            return Result.none;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Result.failure;
        }
    }

    @Override
    public void test(Task x)
    {
        x.test();
    }

    @Override
    public Class<? extends IState> next()
    {
        return ST99Finality.class;
    }

    @Override
    public Class<? extends IState> error()
    {
        return ST99Finality.class;
    }
}
