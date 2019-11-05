package cn.org.hentai.simulator.web.service;

import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.entity.RoutePoint;
import cn.org.hentai.simulator.web.entity.RoutePointExample;
import cn.org.hentai.simulator.web.mapper.RoutePointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by houcheng on 2018/11/25.
 */
@Service
public class XRoutePointService
{
    @Autowired
    RoutePointMapper pointMapper;

    public int save(Route route, List<RoutePoint> points)
    {
        removeByRouteId(route.getId());
        int r = 0;
        for (RoutePoint p : points)
        {
            r += pointMapper.insert(p);
        }
        return r;
    }

    public int batchSave(Route route, List<RoutePoint> points)
    {
        removeByRouteId(route.getId());
        return pointMapper.batchInsert(points);
    }

    public int removeByRouteId(Long routeId)
    {
        return pointMapper.deleteByExample(new RoutePointExample().createCriteria().andRouteIdEqualTo(routeId).example());
    }

    public List<RoutePoint> find(Long routeId)
    {
        return pointMapper.selectByExample(new RoutePointExample().createCriteria().andRouteIdEqualTo(routeId).example());
    }
}
