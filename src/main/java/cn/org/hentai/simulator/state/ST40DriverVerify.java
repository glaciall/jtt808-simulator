package cn.org.hentai.simulator.state;

import cn.org.hentai.simulator.entity.Task;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * Created by houcheng on 2018/11/13.
 * 驾驶员验证
 */
public class ST40DriverVerify extends IState<Task>
{
    @Override
    public String name()
    {
        return "驾驶员认证";
    }

    @Override
    public Result handle(Task task)
    {
        // if (task != null) return Result.success;
        if (StringUtils.isEmpty(task.getDriver().getPhotoPath()) || new File(task.getDriver().getPhotoPath()).exists() == false)
            return Result.success;
        else
        {
            task.driverAuthenticate();
            return Result.wait;
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
        return ST50Report.class;
    }

    @Override
    public Class<? extends IState> error()
    {
        return ST99Finality.class;
    }
}
