package cn.org.hentai.simulator.state;

import cn.org.hentai.simulator.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by houcheng on 2018/11/13.
 * 车机鉴权
 */
public class ST30Verify extends IState<Task>
{
    private static Logger logger = LoggerFactory.getLogger(ST30Verify.class);


    @Override
    public String name()
    {
        return "鉴权";
    }

    @Override
    public Result handle(Task task)
    {
        // 如果已经发送鉴权消息了，那就要不断的测试是否已经有回应了
        // 改为同步连接模式
        task.deviceAuthenticate();
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
        return ST40DriverVerify.class;
    }

    @Override
    public Class<? extends IState> error()
    {
        return ST99Finality.class;
    }
}
