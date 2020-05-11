package cn.org.hentai.simulator.task.eventloop;

import cn.org.hentai.simulator.task.AbstractDriveTask;

/**
 * Created by matrixy on 2020/5/12.
 */
public class ExecutableTask
{
    public AbstractDriveTask driveTask;
    public Executable executable;
    public long executeTime;
    public int interval;

    public ExecutableTask(AbstractDriveTask driveTask, Executable executable, long executeTime, int interval)
    {
        this.driveTask = driveTask;
        this.executable = executable;
        this.executeTime = executeTime;
        this.interval = interval;
    }
}
