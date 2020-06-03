package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.task.TaskManager;
import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.service.RouteService;
import cn.org.hentai.simulator.web.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/batch")
public class BatchController extends BaseController
{
    @Autowired
    RouteService routeService;

    // 批量创建任务入口页面
    @RequestMapping("/index")
    public String index(Model model)
    {
        List<Route> routes = routeService.list();
        model.addAttribute("routes", routes);

        return "task-batch-create";
    }

    // 批量创建
    @RequestMapping("/run")
    @ResponseBody
    public Result run(@RequestParam int vehicleCount,
                       @RequestParam(name = "routeIdList[]", required = false) Long[] routeIdList,
                       @RequestParam String vehicleNumberPattern,
                       @RequestParam String deviceSnPattern,
                       @RequestParam String simNumberPattern,
                       @RequestParam String serverAddress,
                       @RequestParam String serverPort)
    {
        Result result = new Result();
        try
        {
            if (vehicleCount < 1 || vehicleCount > 10_0000)
                throw new RuntimeException("请填写车辆数量，最低1辆，最多100000辆。");

            // 准备线路
            boolean randomRouteMode = false;
            for (Long id : routeIdList)
            {
                if (id == 0)
                {
                    randomRouteMode = true;
                    routeIdList = new Long[0];
                    break;
                }
            }
            List<Route> routes = null;
            if (randomRouteMode) routes = routeService.list();
            else
            {
                routes = new ArrayList(routeIdList.length);
                for (Long id : routeIdList)
                {
                    Route route = routeService.getById(id);
                    if (route != null) routes.add(route);
                }
            }

            Map<String, String> params = new HashMap()
            {
                {
                    put("server.address", serverAddress);
                    put("server.port", serverPort);
                    put("mode", "debug");
                }
            };

            // 创建任务
            TaskManager mgr = TaskManager.getInstance();
            for (int i = 0; i < vehicleCount; i++)
            {
                long idx = mgr.nextIndex();
                params.put("vehicle.number", String.format(vehicleNumberPattern, idx));
                params.put("device.sn", String.format(deviceSnPattern, idx));
                params.put("device.sim", String.format(simNumberPattern, idx));

                long routeId = routes.get((int)(Math.random() * routes.size())).getId();
                mgr.run(params, routeId);
            }
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }
}