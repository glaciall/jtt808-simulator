package cn.org.hentai.simulator.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by houcheng when 2018/11/29.
 */
public class TaskInfo
{
    private Long id;
    private Long routeId;
    private String routeName;
    private Integer routeMileages;

    private Long driverId;
    private String driverName;

    private Boolean alive;

    private Long vehicleId;
    private String vin;
    private String vehicleDeviceSN;

    private String state;
    private Date startTime;
    private Double longitude;
    private Double latitude;

    private List<Point> points;

    public String getRouteName()
    {
        return routeName;
    }

    public TaskInfo setRouteName(String routeName)
    {
        this.routeName = routeName;
        return this;
    }

    public Integer getRouteMileages()
    {
        return routeMileages;
    }

    public TaskInfo setRouteMileages(Integer routeMileages)
    {
        this.routeMileages = routeMileages;
        return this;
    }

    public Boolean getAlive()
    {
        return alive;
    }

    public TaskInfo setAlive(Boolean alive)
    {
        this.alive = alive;
        return this;
    }

    public String getVin()
    {
        return vin;
    }

    public TaskInfo setVin(String vin)
    {
        this.vin = vin;
        return this;
    }

    public Long getId()
    {
        return id;
    }

    public TaskInfo setId(Long id)
    {
        this.id = id;
        return this;
    }

    public Long getDriverId()
    {
        return driverId;
    }

    public TaskInfo setDriverId(Long driverId)
    {
        this.driverId = driverId;
        return this;
    }

    public String getDriverName()
    {
        return driverName;
    }

    public TaskInfo setDriverName(String driverName)
    {
        this.driverName = driverName;
        return this;
    }

    public Long getVehicleId()
    {
        return vehicleId;
    }

    public TaskInfo setVehicleId(Long vehicleId)
    {
        this.vehicleId = vehicleId;
        return this;
    }

    public String getVehicleDeviceSN()
    {
        return vehicleDeviceSN;
    }

    public TaskInfo setVehicleDeviceSN(String vehicleDeviceSN)
    {
        this.vehicleDeviceSN = vehicleDeviceSN;
        return this;
    }

    public String getState()
    {
        return state;
    }

    public TaskInfo setState(String state)
    {
        this.state = state;
        return this;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public TaskInfo setStartTime(Date startTime)
    {
        this.startTime = startTime;
        return this;
    }

    public Long getRouteId()
    {
        return routeId;
    }

    public TaskInfo setRouteId(Long routeId)
    {
        this.routeId = routeId;
        return this;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public TaskInfo setLongitude(Double longitude)
    {
        this.longitude = longitude;
        return this;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public TaskInfo setLatitude(Double latitude)
    {
        this.latitude = latitude;
        return this;
    }

    public List<Point> getPoints()
    {
        return points;
    }

    public TaskInfo setPoints(List<Point> points)
    {
        this.points = points;
        return this;
    }
}
