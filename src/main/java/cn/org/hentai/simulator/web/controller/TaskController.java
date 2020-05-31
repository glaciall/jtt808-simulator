package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.entity.DrivePlan;
import cn.org.hentai.simulator.manager.RouteManager;
import cn.org.hentai.simulator.manager.ScheduleTaskManager;
import cn.org.hentai.simulator.task.SimpleDriveTask;
import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.entity.ScheduleTask;
import cn.org.hentai.simulator.web.service.RouteService;
import cn.org.hentai.simulator.web.service.ScheduleTaskService;
import cn.org.hentai.simulator.web.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by houcheng when 2018/11/25.
 * 线路计划任务控制器
 */
@Controller
@RequestMapping("/task")
public class TaskController
{
    @Autowired
    RouteService routeService;

    @RequestMapping("/index")
    public String index(Model model)
    {
        List<Route> routes = routeService.list();
        model.addAttribute("routes", routes);

        return "/task-create";
    }

    @RequestMapping("/run")
    @ResponseBody
    public Result run(@RequestParam Long routeId,
                      @RequestParam String vehicleNumber,
                      @RequestParam String deviceSn,
                      @RequestParam String simNumber,
                      @RequestParam String serverAddress,
                      @RequestParam String serverPort)
    {
        Result result = new Result();
        try
        {
            if (StringUtils.isEmpty(vehicleNumber) || vehicleNumber.matches("^[\u4e00-\u9fa5]\\w{6,7}$") == false)
                throw new RuntimeException("请填写正确的车牌号");

            if (StringUtils.isEmpty(deviceSn) || deviceSn.matches("^\\w{7,30}$") == false)
                throw new RuntimeException("请填写正确的终端ID");

            if (StringUtils.isEmpty(simNumber) || simNumber.matches("^\\d{11,12}$") == false)
                throw new RuntimeException("请填写正确的终端ID");

            if (StringUtils.isEmpty(serverPort) || serverPort.matches("^\\d{1,5}$") == false)
                throw new RuntimeException("请填写正确的服务器端口");

            if (simNumber.length() < 12) simNumber = ("0000000000000" + simNumber).replaceAll("^0+(\\d{12})$", "$1");

            final String sim = simNumber;

            Map<String, String> params = new HashMap()
            {
                {
                    put("vehicle.number", vehicleNumber);
                    put("device.sn", deviceSn);
                    put("device.sim", sim);
                    put("server.address", serverAddress);
                    put("server.port", serverPort);
                    put("mode", "debug");
                }
            };

            // TODO: 需要检查一下是不是有冲突~~~

            DrivePlan plan = RouteManager.getInstance().generate(routeId, new Date());
            SimpleDriveTask task = new SimpleDriveTask();
            task.init(params, plan);
            task.startup();
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }
}
