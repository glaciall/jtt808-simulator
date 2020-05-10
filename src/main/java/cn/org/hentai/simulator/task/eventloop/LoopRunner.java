package cn.org.hentai.simulator.task.eventloop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by matrixy on 2020/5/8.
 * EventLoop运行线程
 */
public class LoopRunner extends Thread
{
    static Logger logger = LoggerFactory.getLogger(LoopRunner.class);

    // 将执行的任务
    private LinkedList<Task> jobs;

    // 还需要等待的任务
    private TreeSet<Task> tasks;

    private Object lock;

    public LoopRunner()
    {
        lock = new Object();
        tasks = new TreeSet<Task>(new Comparator<Task>()
        {
            @Override
            public int compare(Task o1, Task o2)
            {
                return 0;
            }
        });
        jobs = new LinkedList<Task>();
    }

    // 立即执行（下一个循环）
    public void execute(Task task)
    {
        execute(task, 0);
    }

    // 在milliseconds时间后执行
    public void execute(Task task, int milliseconds)
    {
        synchronized (lock)
        {
            tasks.add(task);
        }
    }

    public void run()
    {
        boolean jobExecuted = false;
        while (!this.isInterrupted())
        {
            try
            {
                if (tasks.isEmpty())
                {
                    Thread.sleep(1);
                    continue;
                }

                long now = System.currentTimeMillis();
                synchronized (lock)
                {
                    while (tasks.isEmpty() == false)
                    {
                        Task task = tasks.first();
                        // TODO: 如果还没有到时间，那就放回去，并且跳出循环
                        if (false)
                        {
                            break;
                        }
                        tasks.remove(task);
                        // 否则加到执行列表里来
                        jobs.addLast(task);
                    }
                }

                // 遍历并执行任务
                for (Task job : jobs)
                {
                    try
                    {
                        job.execute(null);
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
            if (jobExecuted == false) try { Thread.sleep(0, 1000 * 50); } catch(Exception e) { }
        }
    }
}