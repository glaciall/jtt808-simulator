package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.entity.TaskInfo;
import cn.org.hentai.simulator.task.TaskManager;
import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.service.RouteService;
import cn.org.hentai.simulator.web.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/monitor")
public class MapController extends BaseController
{
    @Autowired
    RouteService routeService;

    @Value("${map.baidu.key}")
    String baiduMapKey;

    @RequestMapping("/view")
    public String view(@RequestParam Long id, Model model)
    {
        model.addAttribute("id", id);
        model.addAttribute("baiduMapKey", baiduMapKey);
        return "monitor";
    }

    // 基本信息
    @RequestMapping("/info")
    @ResponseBody
    public Result info(@RequestParam Long id)
    {
        Result result = new Result();
        try
        {
            TaskInfo info = TaskManager.getInstance().getById(id);
            Route route = routeService.getById(info.getRouteId());
            if (route != null)
            {
                info.setRouteName(route.getName());
                info.setRouteMileages(route.getMileages());
            }
            result.setData(info);
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }

    // TODO: 轨迹

    // TODO: 当前位置
    @RequestMapping("/position")
    @ResponseBody
    public Result position(@RequestParam Long id)
    {
        Result result = new Result();
        try
        {
            // ...
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }

    // TODO: 日志

    // TODO：终止行程

    // TODO：状态设置
}