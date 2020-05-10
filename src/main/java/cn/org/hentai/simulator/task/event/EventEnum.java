package cn.org.hentai.simulator.task.event;

/**
 * Created by matrixy on 2020/5/10.
 */
public enum EventEnum
{
    data_received("data_received"),
    data_sent("data_sent"),
    connected("connected"),
    disconnected("disconnected"),
    send_failed("send_failed"),
    message_received("message_received"),
    message_sent("message_sent"),
    user_interact("user_interact");

    private String name;

    EventEnum(String name)
    {
        this.name = name;
    }
}
