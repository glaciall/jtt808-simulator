package cn.org.hentai.simulator.web.service;

import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.entity.StayPoint;
import cn.org.hentai.simulator.web.entity.StayPointExample;
import cn.org.hentai.simulator.web.mapper.StayPointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by houcheng on 2018/11/25.
 */
@Service
public class StayPointService
{
    @Autowired
    StayPointMapper mapper;

    public int create(StayPoint point)
    {
        return mapper.insert(point);
    }

    public int update(StayPoint point)
    {
        return mapper.updateByPrimaryKey(point);
    }

    public int remove(StayPoint point)
    {
        return removeById(point.getId());
    }

    public int removeById(Long id)
    {
        return mapper.deleteByPrimaryKey(id);
    }

    public List<StayPoint> find(Long routeId)
    {
        return mapper.selectByExample(new StayPointExample().createCriteria().andRouteidEqualTo(routeId).example());
    }


    public int save(Route route, List<StayPoint> stayPoints)
    {
        removeByRouteId(route.getId());
        int r = 0;
        for (StayPoint p : stayPoints)
        {
            r += mapper.insert(p);
        }
        return r;
    }

    public int removeByRouteId(Long routeId)
    {
        return mapper.deleteByExample(new StayPointExample().createCriteria().andRouteidEqualTo(routeId).example());
    }
}
