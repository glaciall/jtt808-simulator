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
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 到达时间
     */
    private Long reportTime;

    /**
     * 事件
     * 如果未发生事件，不上报
     */
    private String event;

    /**
     * 速度
     */
    private Float speed;

    /**
     * 方向
     */
    private String direction;

    /**
     * 报警状态位
     */
    private Integer warnFlags;

    /**
     * 车辆状态位
     */
    private Integer status;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getReportTime() {
        return reportTime;
    }

    public void setReportTime(Long reportTime) {
        this.reportTime = reportTime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getWarnFlags()
    {
        return warnFlags;
    }

    public Point setWarnFlags(Integer warnFlags)
    {
        this.warnFlags = warnFlags;
        return this;
    }

    public Integer getStatus()
    {
        return status;
    }

    public Point setStatus(Integer status)
    {
        this.status = status;
        return this;
    }

    @Override
    public String toString()
    {
        return "Point{" + "longitude=" + longitude + ", latitude=" + latitude + ", reportTime=" + reportTime + ", event='" + event + '\'' + ", speed=" + speed + ", direction='" + direction + '\'' + '}';
    }
}
