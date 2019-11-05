package cn.org.hentai.simulator.entity;

/**
 * @Description: 车辆停留点
 * @author:
 * @date: 2018/11/21 9:37
 * @version: V1.0
 */
public class XStayPoint
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
     * 最低时长
     */
    private Integer minTime;

    /**
     * 最高时长
     */
    private Integer maxTime;


    /**
     * 概率
     * 根据概率决定此路段要不要发生停留
     */
    private Integer ratio;


    public XStayPoint(Double longitude, Double latitude, Integer minTime, Integer maxTime, Integer ratio)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.ratio = ratio;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public Integer getMinTime()
    {
        return minTime;
    }

    public void setMinTime(Integer minTime)
    {
        this.minTime = minTime;
    }

    public Integer getMaxTime()
    {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime)
    {
        this.maxTime = maxTime;
    }


    public Integer getRatio()
    {
        return ratio;
    }

    public void setRatio(Integer ratio)
    {
        this.ratio = ratio;
    }
}
