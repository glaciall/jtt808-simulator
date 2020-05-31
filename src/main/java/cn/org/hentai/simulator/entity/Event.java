package cn.org.hentai.simulator.entity;

/**
 * Created by matrixy when 2019-11-04.
 */
public class Event
{
    private String code;
    private String name;

    public String getCode()
    {
        return code;
    }

    public Event setCode(String code)
    {
        this.code = code;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public Event setName(String name)
    {
        this.name = name;
        return this;
    }
}
