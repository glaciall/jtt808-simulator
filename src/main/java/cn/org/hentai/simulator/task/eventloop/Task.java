package cn.org.hentai.simulator.task.eventloop;

import cn.org.hentai.simulator.task.AbstractDriveTask;

/**
 * Created by matrixy on 2020/5/8.
 * 用于LoopRunner执行的任务片断
 */
public interface Task
{
    /**
     * 任务执行入口
     * @param driveTask 行驶行程任务
     */
    public void execute(AbstractDriveTask driveTask);
}
