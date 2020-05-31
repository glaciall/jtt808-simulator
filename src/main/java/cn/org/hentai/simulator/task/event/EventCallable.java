package cn.org.hentai.simulator.task.event;

/**
 * Created by matrixy when 2020/5/8.
 */
public interface EventCallable
{
    /**
     * 当事件发生时调用
     * @param tag 事件标识，如收到服务器端消息、连接状态变化、用户界面交互等
     * @param data 事件发生时带的参数
     */
    public void onEvent(String tag, Object data);
}
