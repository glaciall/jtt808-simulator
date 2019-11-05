package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.entity.Task;
import cn.org.hentai.simulator.manager.SimulatorManager;
import cn.org.hentai.simulator.util.Configs;
import cn.org.hentai.simulator.util.DateUtils;
import cn.org.hentai.simulator.web.service.XDriverPhotoService;
import cn.org.hentai.simulator.web.service.XRouteService;
import cn.org.hentai.simulator.web.vo.Result;
import freemarker.template.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by houcheng on 2018/11/25.
 * 手工创建任务控制器
 */
@Controller
@RequestMapping("/manual")
public class ManualController
{
    @Autowired
    XRouteService routeService;

    @Autowired
    private XDriverPhotoService driverPhotoService;

    /**
     * 跳转到手工创建任务页面
     * @return
     */
    @RequestMapping("/toManual")
    public String toManual() {
        return "/manual/route-manual";
    }

    /**
     * 向模拟器中添加任务
     * @return
     */
    @RequestMapping("/manualTask")
    @ResponseBody
    public Result manualTask(@RequestParam(required = false) Long scheduleTaskId, @RequestParam(required = false) Long routeId,
                             @RequestParam(required = false) Long vehicleId, @RequestParam(required = false) String deviceSN,
                             @RequestParam(required = false) String sim, @RequestParam(required = false) String driverPhoto,
                             @RequestParam(required = false) String fromTime) {

        SimulatorManager manager = SimulatorManager.getInstance();

        // 拼接图片地址
        String path = Configs.get("file.upload.path") + driverPhoto;

        // 加N辆车
        manager.add(new Task(scheduleTaskId, routeId, vehicleId, deviceSN, sim, path, DateUtils.parse(fromTime, "yyyy-MM-dd HH:mm:ss")));

        return new Result().withData(1);

    }

    /**
     * 分页获取线路信息
     * @return
     */
    @RequestMapping("/findRoutes")
    @ResponseBody
    public Result findRoutes(@RequestParam(required = false) String name,
                             @RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return new Result().withData(this.routeService.findPaginate(name, pageIndex, pageSize));
    }


    /**
     * 获取分页的照片列表
     * @param photo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/findPhotoList")
    @ResponseBody
    public Result photoList(@RequestParam(required = false) Long driverId,
                            @RequestParam(required = false) String photo,
                            @RequestParam(defaultValue = "1") int pageIndex,
                            @RequestParam(defaultValue = "10") int pageSize) {
        return new Result().withData(this.driverPhotoService.findPaginate(driverId, photo, pageIndex, pageSize));
    }


}
