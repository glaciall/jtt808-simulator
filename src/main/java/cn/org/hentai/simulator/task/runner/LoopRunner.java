package cn.org.hentai.simulator.task.runner;

import cn.org.hentai.simulator.task.AbstractDriveTask;
import cn.org.hentai.simulator.task.TaskState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created by matrixy when 2020/5/8.
 * EventLoop运行线程
 */
public class LoopRunner extends Thread
{
    static Logger logger = LoggerFactory.getLogger(LoopRunner.class);

    // 将执行的任务
    private LinkedList<ExecutableTask> jobs;

    // 还需要等待的任务
    private TreeSet<ExecutableTask> tasks;

    private Object lock;

    // 下一次执行的时间，也就是休眠到这个时间为止
    private long nextExecuteTime = 0L;

    public LoopRunner()
    {
        lock = new Object();
        tasks = new TreeSet<ExecutableTask>(new Comparator<ExecutableTask>()
        {
            @Override
            public int compare(ExecutableTask o1, ExecutableTask o2)
            {
                long v = o1.executeTime - o2.executeTime;
                return v == 0 ? 0 : v > 0 ? 1 : -1;
            }
        });
        jobs = new LinkedList<ExecutableTask>();
    }

    // 立即执行（下一个循环）
    public void execute(AbstractDriveTask driveTask, Executable task)
    {
        execute(driveTask, task, 0, 0);
    }

    // 在milliseconds时间后执行
    public void execute(AbstractDriveTask driveTask, Executable task, int milliseconds, int interval)
    {
        execute(new ExecutableTask(driveTask, task, System.currentTimeMillis() + milliseconds, interval));
    }

    public void execute(ExecutableTask task)
    {
        synchronized (lock)
        {
            tasks.add(task);
            // 如果需要执行的时间是在线程休眠时间前，那需要唤醒线程
            if (task.executeTime < nextExecuteTime) lock.notify();
        }
    }

    public void run()
    {
        while (!this.isInterrupted())
        {
            try
            {
                long ms = 0;
                long now = System.currentTimeMillis();
                synchronized (lock)
                {
                    while (tasks.isEmpty() == false)
                    {
                        ExecutableTask task = tasks.first();
                        if (now < task.executeTime)
                        {
                            // 如果还没有到时间，那就跳出循环
                            ms = task.executeTime - now;
                            break;
                        }
                        else
                        {
                            // 否则加到执行列表里来
                            tasks.remove(task);
                            jobs.addLast(task);
                        }
                    }
                }

                if (jobs.isEmpty())
                {
                    synchronized (lock)
                    {
                        ms = Math.max(ms, 1);
                        nextExecuteTime = System.currentTimeMillis() + ms;
                        lock.wait(ms);
                    }
                    continue;
                }

                // 遍历并执行任务
                for (ExecutableTask task : jobs)
                {
                    try
                    {
                        // 跳过已经终止的行程任务
                        if (task.driveTask.getState().equals(TaskState.terminated) == true) continue;

                        task.executable.execute(task.driveTask);
                        if (task.interval > 0)
                        {
                            task.executeTime += task.interval;
                            execute(task);
                        }
                    }
                    catch(Exception e)
                    {
                        logger.error("job execute failed", e);
                    }
                }

                jobs.clear();
            }
            catch(Exception ex)
            {
                logger.error("execute failed", ex);
            }
        }
    }
}