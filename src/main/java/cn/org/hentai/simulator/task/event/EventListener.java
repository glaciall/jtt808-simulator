package cn.org.hentai.simulator.task.event;

import cn.org.hentai.simulator.task.AbstractDriveTask;

/**
 * Created by matrixy when 2020/5/8.
 * 事件触发时调用的事件回调，完成对具体事件的执行处理过程
 */
public interface EventListener
{
    /**
     * 当事件发生时调用
     * @param driveTask 当前行驶行程
     * @param data 事件数据
     */
    public void on(AbstractDriveTask driveTask, Object data);
}
