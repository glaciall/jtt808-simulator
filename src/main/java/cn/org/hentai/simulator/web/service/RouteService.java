package cn.org.hentai.simulator.web.service;

import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.entity.RouteExample;
import cn.org.hentai.simulator.web.mapper.RouteMapper;
import cn.org.hentai.simulator.web.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by houcheng on 2018/11/25.
 */
@Service
public class RouteService
{
    @Autowired
    RouteMapper routeMapper;

    public int create(Route route)
    {
        return routeMapper.insert(route);
    }

    public int update(Route route)
    {
        return routeMapper.updateByPrimaryKey(route);
    }

    public int remove(Route route)
    {
        return removeById(route.getId());
    }

    public int removeById(Long id)
    {
        return routeMapper.deleteByPrimaryKey(id);
    }

    public Route getById(Long id)
    {
        return routeMapper.selectByPrimaryKey(id);
    }

    public List<Route> list()
    {
        return routeMapper.selectByExample(new RouteExample());
    }

    public Page<Route> find(String name, int pageIndex, int pageSize)
    {
        Page<Route> page = new Page<Route>(pageIndex, pageSize);
        RouteExample.Criteria criteria = new RouteExample().createCriteria();
        criteria.andMinSpeedIsNotNull();
        if (!StringUtils.isEmpty(name))
        {
            criteria.andNameLike("%" + name + "%");
        }

        criteria.example().setPageInfo(pageIndex, pageSize);
        page.setList(routeMapper.selectByExample(criteria.example()));
        page.setRecordCount(routeMapper.countByExample(criteria.example()));
        return page;
    }
}
