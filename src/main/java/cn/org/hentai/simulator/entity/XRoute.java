package cn.org.hentai.simulator.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 车辆运行线路
 * @author:
 * @date: 2018/11/21 9:27
 * @version: V1.0
 */
public class XRoute
{

    private Long id;

    /**
     * 轨迹节列表
     */
    private List<Position> positionList;

    /**
     * 问题路段
     */
    private List<XTroubleSegment> troubleSegmentList = new ArrayList();

    /**
     * 停留点
     */
    private LinkedList<XStayPoint> vehicleStayPointList = new LinkedList();

    /**
     * 时速下限
     */
    private Integer minSpeed;

    /**
     * 时速上线
     */
    private Integer maxSpeed;

    private String fingerPrint;

    public String getFingerPrint()
    {
        return fingerPrint;
    }

    public XRoute setFingerPrint(String fingerPrint)
    {
        this.fingerPrint = fingerPrint;
        return this;
    }

    public XRoute()
    {

    }

    public Long getId()
    {
        return id;
    }

    public XRoute setId(Long id)
    {
        this.id = id;
        return this;
    }

    public Integer getMinSpeed()
    {
        return minSpeed;
    }

    public XRoute setMinSpeed(Integer minSpeed)
    {
        this.minSpeed = minSpeed;
        return this;
    }

    public Integer getMaxSpeed()
    {
        return maxSpeed;
    }

    public XRoute setMaxSpeed(Integer maxSpeed)
    {
        this.maxSpeed = maxSpeed;
        return this;
    }

    public XRoute(Long routeId)
    {
        this.id = routeId;
    }


    public List<Position> getPositionList()
    {
        return positionList;
    }

    public void setPositionList(List<Position> positionList)
    {
        this.positionList = positionList;
    }

    public XRoute withPositionList(List<Position> positionList)
    {
        this.setPositionList(positionList);
        return this;
    }


    public List<XTroubleSegment> getTroubleSegmentList()
    {
        return troubleSegmentList;
    }

    public void setTroubleSegmentList(List<XTroubleSegment> troubleSegmentList)
    {
        this.troubleSegmentList = troubleSegmentList;
    }

    public XRoute withTroubleSegmentList(List<XTroubleSegment> troubleSegmentList)
    {
        this.setTroubleSegmentList(troubleSegmentList);
        return this;
    }

    public List<XStayPoint> getVehicleStayPointList()
    {
        return vehicleStayPointList;
    }

    public void setVehicleStayPointList(LinkedList<XStayPoint> vehicleStayPointList)
    {
        this.vehicleStayPointList = vehicleStayPointList;
    }

    public XRoute withVehicleStayPointList(LinkedList<XStayPoint> vehicleStayPointList)
    {
        this.setVehicleStayPointList(vehicleStayPointList);
        return this;
    }
}