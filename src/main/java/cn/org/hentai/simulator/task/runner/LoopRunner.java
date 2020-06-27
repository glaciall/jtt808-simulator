package cn.org.hentai.simulator.task.runner;

import cn.org.hentai.simulator.task.AbstractDriveTask;
import cn.org.hentai.simulator.task.TaskState;
import cn.org.hentai.simulator.util.RBTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by matrixy when 2020/5/8.
 * EventLoop运行线程
 */
public class LoopRunner extends Thread
{
    static Logger logger = LoggerFactory.getLogger(LoopRunner.class);

    // 将执行的任务
    private LinkedList<ExecutableTask> jobs;

    // 所有待执行的任务组（每个时刻的任务列表），通过红黑树保存起来，每次只获取最小（最近）的一条
    private RBTree<TaskGroup> scheduleTasks;

    private Object lock;

    // 下一次执行的时间，也就是休眠到这个时间为止
    private long nextExecuteTime = 0L;

    public LoopRunner()
    {
        lock = new Object();
        // tasks需要一个有序的，并且能够容忍同权重的容器
        scheduleTasks = new RBTree<TaskGroup>();
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
        TaskGroup tmp = new TaskGroup(task.executeTime);
        synchronized (lock)
        {
            // scheduleTasks.add(task);
            RBTree.RBTNode item = scheduleTasks.search(tmp);
            if (item != null && item.getKey() != null)
            {
                tmp = (TaskGroup) item.getKey();
                tmp.add(task);
            }
            else
            {
                tmp = new TaskGroup(task.executeTime);
                tmp.add(task);
                scheduleTasks.insert(tmp);
            }

            // 如果需要执行的时间是在线程休眠时间前，那需要唤醒线程
            if (task.executeTime < nextExecuteTime) lock.notify();
        }
    }

    public void run()
    {
        loop : while (!this.isInterrupted())
        {
            try
            {
                long ms = 0;
                long now = System.currentTimeMillis();
                synchronized (lock)
                {
                    while (true)
                    {
                        TaskGroup group = scheduleTasks.minimum();
                        // 如果没有需要执行的任务
                        if (group == null) break;
                        // 如果最近的任务的时间还没有到
                        if (group.time > now)
                        {
                            // 如果时间还没有到，那就看还差多久，就休眠多久
                            ms = group.time - now;
                            break;
                        }

                        // 删掉它，全部转移到待执行的列表上来
                        scheduleTasks.remove(group);
                        jobs.addAll(group.tasks);
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
                        if (task.driveTask.getState().equals(TaskState.terminated) == true)
                        {
                            continue;
                        }

                        task.executable.execute(task.driveTask);
                        if (task.interval > 0)
                        {
                            task.executeTime += task.interval;
                            execute(task);
                        }
                    }
                    catch(Exception e)
                    {
                        logger.info("job execute failed", e);
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

    static final AtomicLong xxoo = new AtomicLong(0L);
    public static void main(String[] args) throws Exception
    {
        final LoopRunner runner = new LoopRunner();
        runner.start();

        for (int i = 0; i < 10; i++)
        {
            new Thread()
            {
                public void run()
                {
                    for (int i = 0; i < 1000; i++)
                    {
                        // task.executeTime = System.currentTimeMillis();
                        final ExecutableTask task = new ExecutableTask(null, new Executable()
                        {
                            @Override
                            public void execute(AbstractDriveTask driveTask)
                            {
                                System.out.println(xxoo.addAndGet(1L));
                            }
                        }, 0, 0);
                        // TODO: treeset的去重。。。相同的会去掉
                        runner.execute(task);
                    }
                }
            }.start();
        }
    }
}