package cn.org.hentai.simulator.task;

public enum TaskState
{
    idle("idle", "空闲"),
    driving("driving", "行驶中"),
    parking("parking", "停车中"),
    terminated("terminated", "行驶终止");

    String name;
    String value;

    TaskState(String value, String name)
    {
        this.value = value;
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public String getValue()
    {
        return this.value;
    }
}