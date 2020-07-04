package cn.org.hentai.simulator.entity;

import java.io.Serializable;

/**
 * @Description: 车辆行驶轨迹
 * @author:
 * @date: 2018/11/21 9:54
 * @version: V1.0
 */
public class Point implements Serializable
{
    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;

    /**
     * 到达时间
     */
    private long reportTime;

    /**
     * 速度
     */
    private float speed;

    /**
     * 方向
     */
    private int direction;

    /**
     * 报警状态位
     */
    private int warnFlags;

    private int mileages;

    /**
     * 车辆状态位
     */
    private int status;

    private boolean isStay;

    public Point()
    {
        this.mileages = 0;
    }

    public int getMileages()
    {
        return mileages;
    }

    public void setMileages(int mileages)
    {
        this.mileages = mileages;
    }

    public boolean isStay()
    {
        return isStay;
    }

    public void setStay(boolean stay)
    {
        isStay = stay;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public long getReportTime()
    {
        return reportTime;
    }

    public void setReportTime(long reportTime)
    {
        this.reportTime = reportTime;
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public int getWarnFlags()
    {
        return warnFlags;
    }

    public void setWarnFlags(int warnFlags)
    {
        this.warnFlags = warnFlags;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "Point{" + "longitude=" + longitude + ", latitude=" + latitude + ", reportTime=" + reportTime + ", speed=" + speed + ", direction='" + direction + '\'' + '}';
    }
}
