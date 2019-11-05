package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.entity.Task;
import cn.org.hentai.simulator.manager.ScheduleTaskManager;
import cn.org.hentai.simulator.manager.SimulatorManager;
import cn.org.hentai.simulator.util.SIMGenerator;
import cn.org.hentai.simulator.web.entity.DriverPhoto;
import cn.org.hentai.simulator.web.entity.ScheduleTask;
import cn.org.hentai.simulator.web.service.XDriverPhotoService;
import cn.org.hentai.simulator.web.service.XRouteService;
import cn.org.hentai.simulator.web.service.XScheduleTaskService;
import cn.org.hentai.simulator.web.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by houcheng on 2018/11/25.
 * 线路计划任务控制器
 */
@Controller
@RequestMapping("/task")
public class TaskController
{
    @Autowired
    XRouteService routeService;

    @Autowired
    private XScheduleTaskService scheduleTaskService;

    @Autowired
    XDriverPhotoService driverPhotoService;

    /**
     * 跳转到线路计划任务列表
     * @return
     */
    @RequestMapping("/")
    public String index()
    {
        // TODO: 可以考虑在这一步删除掉名称为“新建线路”的记录
        return "/task/index";
    }

    /**
     * 添加任务信息
     * @param task
     * @return
     */
    @RequestMapping("/createTask")
    @ResponseBody
    public Result createTask(ScheduleTask task)
    {
        Result result = new Result().withData(this.scheduleTaskService.create(task));
        ScheduleTaskManager.getInstance().load(task);
        return result;
    }

    @RequestMapping("/run")
    @ResponseBody
    public Result run(@RequestParam Long scheduleTaskId)
    {
        Result result = new Result();
        try
        {
            ScheduleTask scheduleTask = scheduleTaskService.getById(scheduleTaskId);
            // VehicleDevice device = deviceService.getByVehicleId(scheduleTask.getVehicleId());
            // if (null == device) throw new RuntimeException("未查询到此车辆所绑定的车机");

            List<DriverPhoto> photos = driverPhotoService.findByDriverId(scheduleTask.getDriverId());
            if (photos == null || photos.size() == 0) throw new RuntimeException("线程计划的驾驶员未设定照片");

            // Task task = new Task(scheduleTaskId, scheduleTask.getRouteId(), scheduleTask.getVehicleId(), device.getDeviceSN(), String.valueOf(SIMGenerator.get()), ConfigUtil.getConfig("file.upload.path") + photos.get((int)(Math.random() * photos.size())).getPhoto(), new Date());
            // SimulatorManager.getInstance().add(task);
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }

    /**
     * 根据ID删除任务
     * @param id
     * @return
     */
    @RequestMapping("/remove")
    @ResponseBody
    public Result remove(@RequestParam Long id) {
        this.scheduleTaskService.removeById(id);
        return new Result().withData(1);
    }

    /**
     * 跳转到添加任务页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/task/route-add";
    }

    /**
     * 修改任务信息
     * @param task
     * @return
     */
    @RequestMapping("/editTask")
    @ResponseBody
    public Result editTask(ScheduleTask task) {
        ScheduleTask curTask = this.scheduleTaskService.getById(task.getId());
        if (curTask == null) {
            return new Result().withData(null);
        }

        curTask.setRouteId(task.getRouteId());
        curTask.setDriverId(task.getDriverId());
        curTask.setVehicleId(task.getVehicleId());
        curTask.setFromTime(task.getFromTime());
        curTask.setEndTime(task.getEndTime());
        curTask.setRatio(task.getRatio());
        curTask.setDaysInterval(task.getDaysInterval());

        return new Result().withData(this.scheduleTaskService.update(curTask));
    }
}
