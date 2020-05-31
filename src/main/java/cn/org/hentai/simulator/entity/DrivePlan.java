package cn.org.hentai.simulator.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by houcheng when 2019/1/6.
 */
public class DrivePlan implements Serializable
{
    LinkedList<Point> routePoints;

    public LinkedList<Point> getRoutePoints()
    {
        return routePoints;
    }

    public DrivePlan setRoutePoints(LinkedList<Point> routePoints)
    {
        this.routePoints = routePoints;
        return this;
    }

    public Point getNextPoint()
    {
        if (routePoints.isEmpty()) return null;
        return routePoints.removeFirst();
    }
}
