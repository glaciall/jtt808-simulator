package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.entity.TaskInfo;
import cn.org.hentai.simulator.manager.RouteManager;
import cn.org.hentai.simulator.task.TaskManager;
import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.service.RouteService;
import cn.org.hentai.simulator.web.vo.Page;
import cn.org.hentai.simulator.web.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/monitor/list")
public class MonitorController extends BaseController
{
    @Autowired
    RouteService routeService;

    @RequestMapping("/index")
    public String index()
    {
        return "monitor-list-index";
    }

    @RequestMapping("/json")
    @ResponseBody
    public Result listJson(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "20") int pageSize)
    {
        Result result = new Result();
        try
        {
            Page<TaskInfo> page = TaskManager.getInstance().find(pageIndex, pageSize);
            for (TaskInfo task : page.getList())
            {
                Route route = routeService.getById(task.getRouteId());
                if (route != null)
                {
                    task.setRouteName(route.getName());
                    task.setRouteMileages(route.getMileages());
                }
            }
            result.setData(page);
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }
}