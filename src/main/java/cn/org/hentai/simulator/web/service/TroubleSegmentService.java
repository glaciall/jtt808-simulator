package cn.org.hentai.simulator.web.service;

import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.entity.TroubleSegment;
import cn.org.hentai.simulator.web.entity.TroubleSegmentExample;
import cn.org.hentai.simulator.web.mapper.TroubleSegmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by houcheng on 2018/11/25.
 */
@Service
public class TroubleSegmentService
{
    @Autowired
    TroubleSegmentMapper mapper;

    public int create(TroubleSegment segment)
    {
        return mapper.insert(segment);
    }

    public int update(TroubleSegment segment)
    {
        return mapper.updateByPrimaryKey(segment);
    }

    public int remove(TroubleSegment segment)
    {
        return removeById(segment.getId());
    }

    public int removeById(Long id)
    {
        return mapper.deleteByPrimaryKey(id);
    }

    public List<TroubleSegment> find(Long routeId)
    {
        return mapper.selectByExample(new TroubleSegmentExample().createCriteria().andRouteIdEqualTo(routeId).example());
    }


    public int save(Route route, List<TroubleSegment> troubleSegments)
    {
        removeByRouteId(route.getId());
        int r = 0;
        for (TroubleSegment p : troubleSegments)
        {
            r += mapper.insert(p);
        }
        return r;
    }

    public int removeByRouteId(Long routeId)
    {
        return mapper.deleteByExample(new TroubleSegmentExample().createCriteria().andRouteIdEqualTo(routeId).example());
    }
}
