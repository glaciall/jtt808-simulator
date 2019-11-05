package cn.org.hentai.simulator.state;

import cn.org.hentai.simulator.entity.Task;

/**
 * Created by houcheng on 2019/2/28.
 */
public class ST21Register extends IState<Task>
{
    @Override
    public Result handle(Task x)
    {
        x.getVehicleDevice().register();
        return Result.wait;
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

    @Override
    public String name()
    {
        return "注册";
    }
}
