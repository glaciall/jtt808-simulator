package cn.org.hentai.simulator.task.eventloop;

import cn.org.hentai.simulator.task.AbstractDriveTask;

/**
 * Created by matrixy on 2020/5/8.
 * LoopRunner线程管理器，用于分发/管理任务
 */
public final class RunnerManager
{
    static final RunnerManager instance = new RunnerManager();

    // LoopRunner线程组
    LoopRunner[] runners;

    private RunnerManager()
    {
        runners = new LoopRunner[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < runners.length; i++)
        {
            runners[i] = new LoopRunner();
            runners[i].setName("loop-runner-" + i);
            runners[i].start();
        }
    }

    // 只要在应用程序启动时调用一下这个方法，就可以完成本类的static变量的初始化，省得加锁了
    public static void init()
    {
        // do nothing here...
    }

    public static RunnerManager getInstance()
    {
        return instance;
    }

    // 委托运行某任务
    public void execute(AbstractDriveTask driveTask, Task task)
    {
        execute(driveTask, task, 0);
    }

    // 委托在milliseconds时间后运行某任务
    public void execute(AbstractDriveTask driveTask, Task task, int milliseconds)
    {
        int hash = Math.abs(driveTask.hashCode() % runners.length);
        runners[hash].execute(task, milliseconds);
    }

    // 关闭线程组里的所有LoopRunner
    public void shutdown()
    {
        for (LoopRunner runner : runners)
        {
            runner.interrupt();
        }
    }
}
