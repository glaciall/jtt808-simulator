package cn.org.hentai.simulator.task.eventloop;

import cn.org.hentai.simulator.task.AbstractDriveTask;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by matrixy on 2020/5/12.
 */
public class ExecutableTask
{
    static final AtomicLong SEQ = new AtomicLong(0L);

    public AbstractDriveTask driveTask;
    public Executable executable;
    public long executeTime;
    public int interval;
    private long id;

    public ExecutableTask(AbstractDriveTask driveTask, Executable executable, long executeTime, int interval)
    {
        this.driveTask = driveTask;
        this.executable = executable;
        this.executeTime = executeTime;
        this.interval = interval;

        this.id = SEQ.addAndGet(1L);
    }

    // 必须要重载以下两个方法，否则在添加到TreeSet中时会失败
    @Override
    public int hashCode()
    {
        return (int)id;
    }

    @Override
    public boolean equals(Object obj)
    {
        ExecutableTask o2 = (ExecutableTask) obj;
        return this.hashCode() == o2.hashCode() && this == o2;
    }
}
