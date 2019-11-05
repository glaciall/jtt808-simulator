package cn.org.hentai.simulator.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by houcheng on 2019/1/6.
 */
public class DrivePlan
{
    LinkedList<Point> routePoints;
    List<XEvent> events;

    public LinkedList<Point> getRoutePoints()
    {
        return routePoints;
    }

    public DrivePlan setRoutePoints(LinkedList<Point> routePoints)
    {
        this.routePoints = routePoints;
        return this;
    }

    public List<XEvent> getEvents()
    {
        return events;
    }

    public DrivePlan setEvents(List<XEvent> events)
    {
        this.events = events;
        return this;
    }
}
