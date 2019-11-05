package cn.org.hentai.simulator.state;

import cn.org.hentai.simulator.entity.Task;

/**
 * Created by houcheng on 2018/11/13.
 * 建立连接
 */
public class ST20Connect extends IState<Task>
{
    @Override
    public String name()
    {
        return "建立连接";
    }

    @Override
    public Result handle(Task task)
    {
        task.connect();
        return Result.success;
    }

    @Override
    public Class<? extends IState> next()
    {
        return ST30Verify.class;
    }

    @Override
    public Class<? extends IState> error()
    {
        return ST99Finality.class;
    }
}
