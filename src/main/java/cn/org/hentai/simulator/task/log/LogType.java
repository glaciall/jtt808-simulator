package cn.org.hentai.simulator.task.log;

public enum LogType
{
    NETWORK("网络"), MESSAGE_IN("消息下行"), MESSAGE_OUT("消息上行"), EXCEPTION("异常"), INFO("信息"), USER_TRIGGER("用户触发");

    String name;

    LogType(String name)
    {
        this.name = name;
    }
}