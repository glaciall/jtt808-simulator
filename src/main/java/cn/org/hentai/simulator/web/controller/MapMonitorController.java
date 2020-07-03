package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.entity.Point;
import cn.org.hentai.simulator.entity.TaskInfo;
import cn.org.hentai.simulator.task.TaskManager;
import cn.org.hentai.simulator.task.log.Log;
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

import java.util.List;

@Controller
@RequestMapping("/monitor")
public class MapMonitorController extends BaseController
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
    public Result position(@RequestParam Long id, @RequestParam Long time)
    {
        Result result = new Result();
        try
        {
            Point point = TaskManager.getInstance().getCurrentPositionById(id);
            if (point != null && point.getReportTime() > time)
            {
                result.setData(point);
            }
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }

    // TODO: 日志
    @RequestMapping("/logs")
    @ResponseBody
    public Result logs(@RequestParam Long id, @RequestParam(defaultValue = "0") Long timeAfter)
    {
        Result result = new Result();
        try
        {
            List<Log> logs = TaskManager.getInstance().getLogsById(id, timeAfter);

            result.setData(logs);
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }

    // TODO：终止行程
    @RequestMapping("/terminate")
    @ResponseBody
    public Result terminate(@RequestParam Long id)
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

    // TODO：状态设置
    @RequestMapping("/bit/set")
    @ResponseBody
    public Result setBit(@RequestParam Long id, @RequestParam String type, @RequestParam int bitIndex, @RequestParam Boolean on)
    {
        Result result = new Result();
        try
        {
            if ("warning-flags".equals(type))
            {
                TaskManager.getInstance().setWarningFlagById(id, bitIndex, on);
            }
            if ("state-flags".equals(type))
            {
                TaskManager.getInstance().setStateFlagById(id, bitIndex, on);
            }
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }
}