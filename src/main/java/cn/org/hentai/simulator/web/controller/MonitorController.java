package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.entity.Point;
import cn.org.hentai.simulator.entity.TaskInfo;
import cn.org.hentai.simulator.manager.SimulatorManager;
import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.service.RouteService;
import cn.org.hentai.simulator.web.vo.Page;
import cn.org.hentai.simulator.web.vo.Result;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by houcheng on 2018/11/29.
 * 实时监控控制器
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController
{
    @Autowired
    RouteService routeService;

    /**
     * 跳转到任务监控页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList()
    {
        return "/monitor/route-monitor";
    }

    /**
     * 任务监控列表
     * @param routeId
     * @param driverId
     * @param vehicleId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestParam(required = false) Long routeId,
                       @RequestParam(required = false) Long driverId,
                       @RequestParam(required = false) Long vehicleId,
                       @RequestParam(defaultValue = "1") int pageIndex,
                       @RequestParam(defaultValue = "50") int pageSize)
    {
        String deviceSN = null;
        Page<TaskInfo> page = SimulatorManager.getInstance().find(routeId, driverId, deviceSN, pageIndex, pageSize);
        for (TaskInfo info : page.getList())
        {
            Route route = routeService.getById(info.getRouteId());
            info.setRouteName(route == null ? "[线路已删除]" : route.getName());
            info.setRouteMileages(route == null ? 0 : route.getMileages());

            info.setVin("--");
        }
        return new Result().withData(page);
    }

    @RequestMapping("/stopTask")
    @ResponseBody
    public Result stopTask(@RequestParam Long id)
    {
        SimulatorManager.getInstance().notify(id, SimulatorManager.Command.stop);
        return new Result();
    }

    @RequestMapping("/stat")
    @ResponseBody
    public Result stat()
    {
        Result result = new Result();
        try
        {
            SimulatorManager manager = SimulatorManager.getInstance();

            long[] lastLoopBefore = manager.getLastActiveTime();
            for (int i = 0; i < lastLoopBefore.length; i++) lastLoopBefore[i] = System.currentTimeMillis() - lastLoopBefore[i];

            result.setData(Result.values(
                    "lastLoopSpend", manager.getLastLoopSpend(),
                    "runningTaskCount", manager.getRunningCount(),
                    "send", 0,
                    "receive", 0,
                    "lastLoopBefore", lastLoopBefore,
                    "state", manager.getTaskStatistics()
            ));
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }

    /**
     * 根据任务ID获取详情
     * @param id 任务ID
     * @return
     */
    @RequestMapping("/info")
    public String getById(@RequestParam Long id, Model model) {

        TaskInfo info = SimulatorManager.getInstance().getTaskById(id);
        if (info == null) {
            info = new TaskInfo();
        }
        Route route = routeService.getById(info.getRouteId());

        model.addAttribute("task", info);
        model.addAttribute("route", route);

        // 防止出现 List出现 ConcurrentModificationException异常
        List<Point> points = new LinkedList<Point>();
        points.addAll(info.getPoints());
        model.addAttribute("taskJson", new Gson().toJson(points));

        return "/monitor/task-info";
    }
}
