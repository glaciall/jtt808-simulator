package cn.org.hentai.simulator.entity;

import cn.org.hentai.simulator.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by houcheng on 2019/1/6.
 */
public class XEvent implements Serializable
{
    private String code;
    private int type;
    private int sequence;
    private long reportTime;
    private int time1;
    private int time2;
    private double longitude;
    private double latitude;
    private float speed;

    public double getLongitude()
    {
        return longitude;
    }

    public XEvent setLongitude(double longitude)
    {
        this.longitude = longitude;
        return this;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public XEvent setLatitude(double latitude)
    {
        this.latitude = latitude;
        return this;
    }

    public float getSpeed()
    {
        return speed;
    }

    public XEvent setSpeed(float speed)
    {
        this.speed = speed;
        return this;
    }

    public int getTime1()
    {
        return time1;
    }

    public XEvent setTime1(int time1)
    {
        this.time1 = time1;
        return this;
    }

    public int getTime2()
    {
        return time2;
    }

    public XEvent setTime2(int time2)
    {
        this.time2 = time2;
        return this;
    }

    public String getCode()
    {
        return code;
    }

    public XEvent setCode(String code)
    {
        this.code = code;
        return this;
    }

    public int getType()
    {
        return type;
    }

    public XEvent setType(int type)
    {
        this.type = type;
        return this;
    }

    public int getSequence()
    {
        return sequence;
    }

    public XEvent setSequence(int sequence)
    {
        this.sequence = sequence;
        return this;
    }

    public long getReportTime()
    {
        return reportTime;
    }

    public XEvent setReportTime(long reportTime)
    {
        this.reportTime = reportTime;
        return this;
    }

    public String getTime()
    {
        return DateUtils.format(new Date(this.reportTime), "yyyy-MM-dd HH:mm:ss");
    }
}
