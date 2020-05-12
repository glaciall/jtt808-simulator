package cn.org.hentai.simulator.task.eventloop;

import cn.org.hentai.simulator.task.AbstractDriveTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created by matrixy on 2020/5/8.
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
                    Thread.sleep(Math.max(ms, 1));
                    continue;
                }

                // 遍历并执行任务
                for (ExecutableTask task : jobs)
                {
                    try
                    {
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