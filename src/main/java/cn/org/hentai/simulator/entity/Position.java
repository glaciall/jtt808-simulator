package cn.org.hentai.simulator.entity;

import java.util.Date;

/**
 * Created by matrixy when 2019-11-04.
 */
public class Position
{
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Date reportTime;

    public Position(Double lng, Double lat)
    {
        this.longitude = lng;
        this.latitude = lat;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public Position setLongitude(Double longitude)
    {
        this.longitude = longitude;
        return this;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public Position setLatitude(Double latitude)
    {
        this.latitude = latitude;
        return this;
    }

    public Double getAltitude()
    {
        return altitude;
    }

    public Position setAltitude(Double altitude)
    {
        this.altitude = altitude;
        return this;
    }

    public Date getReportTime()
    {
        return reportTime;
    }

    public Position setReportTime(Date reportTime)
    {
        this.reportTime = reportTime;
        return this;
    }

    @Override
    public String toString()
    {
        return "Position{" + "longitude=" + longitude + ", latitude=" + latitude + ", altitude=" + altitude + ", reportTime=" + reportTime + '}';
    }
}
