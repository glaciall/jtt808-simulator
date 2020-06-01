package cn.org.hentai.simulator.task;

import cn.org.hentai.simulator.entity.DrivePlan;
import cn.org.hentai.simulator.entity.TaskInfo;
import cn.org.hentai.simulator.manager.RouteManager;
import cn.org.hentai.simulator.web.vo.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 行程任务管理器
 */
public final class TaskManager
{
    static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    Object lock;
    LinkedList<AbstractDriveTask> tasks;
    AtomicLong sequence;

    private TaskManager()
    {
        this.lock = new Object();
        this.tasks = new LinkedList<>();
        this.sequence = new AtomicLong(0L);
    }

    /**
     * 按给定的参数集，开启任务
     * @param params
     * @param routeId
     */
    public void run(Map params, Long routeId)
    {
        // TODO: 需要检查一下是不是有冲突~~~
        DrivePlan plan = RouteManager.getInstance().generate(routeId, new Date());

        AbstractDriveTask task = new SimpleDriveTask(this.sequence.addAndGet(1L));
        task.init(params, plan);
        task.startup();

        synchronized (lock)
        {
            tasks.add(task);
        }
    }

    // 分页查找，用于列表显示运行中的行程任务状态
    public Page<TaskInfo> find(int pageIndex, int pageSize)
    {
        synchronized (lock)
        {
            Page<TaskInfo> page = new Page(pageIndex, pageSize);
            page.setRecordCount(tasks.size());
            LinkedList<TaskInfo> list = new LinkedList<>();
            for (AbstractDriveTask task : tasks.subList(Math.max(pageIndex - 1 * pageSize, 0), Math.min(pageIndex * pageSize, tasks.size())))
            {
                list.add(task.getInfo());
            }
            page.setList(list);
            return page;
        }
    }

    static final TaskManager instance = new TaskManager();
    public static void init()
    {
        // ...
    }
    public static TaskManager getInstance()
    {
        return instance;
    }
}