package cn.org.hentai.simulator.task.runner;

import cn.org.hentai.simulator.task.AbstractDriveTask;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by matrixy when 2020/5/12.
 */
public class ExecutableTask
{
    static final AtomicLong SEQ = new AtomicLong(0L);

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
