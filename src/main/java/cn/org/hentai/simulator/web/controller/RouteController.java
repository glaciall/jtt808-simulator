package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.manager.RouteManager;
import cn.org.hentai.simulator.util.MD5;
import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.entity.RoutePoint;
import cn.org.hentai.simulator.web.entity.StayPoint;
import cn.org.hentai.simulator.web.entity.TroubleSegment;
import cn.org.hentai.simulator.web.service.XRoutePointService;
import cn.org.hentai.simulator.web.service.XRouteService;
import cn.org.hentai.simulator.web.service.XStayPointService;
import cn.org.hentai.simulator.web.service.XTroubleSegmentService;
import cn.org.hentai.simulator.web.vo.Result;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by houcheng on 2018/11/25.
 * 线路控制器
 */
@Controller
@RequestMapping("/route")
public class RouteController
{
    @Autowired
    XRouteService routeService;

    @Autowired
    private XRoutePointService routePointService;

    @Autowired
    private XStayPointService stayPointService;

    @Autowired
    private XTroubleSegmentService troubleSegmentService;



    /**
     * 跳转到线路列表
     * @param model
     * @return
     */
    @RequestMapping("/routeList")
    public String toRouteList(Model model)
    {
        return "/route/route-list";
    }

    /**
     * 创建线路并跳转到编辑页面
     * @return
     */
    @RequestMapping("/create")
    public String create()
    {
        Route route = new Route();
        route.setId(System.currentTimeMillis());
        route.setName("新建线路");
        routeService.create(route);
        return "redirect:/route/edit?id=" + route.getId();
    }

    /**
     * 跳转到编辑页面
     * @param routeId
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam(name = "id") Long routeId, Model model)
    {
        // 在这里可以把停留点、轨迹点、问题路段等全部加载进来，用于编辑处理
        model.addAttribute("routeId", routeId);

        // 获取线路信息
        model.addAttribute("route", routeService.getById(routeId));
        // 获取轨迹点
        model.addAttribute("routePoints", new Gson().toJson(this.routePointService.find(routeId)).toString());
        // 获取问题路段
        model.addAttribute("troubleSegments", new Gson().toJson(this.troubleSegmentService.find(routeId)).toString());
        // 获取停留点
        model.addAttribute("stayPoints", new Gson().toJson(this.stayPointService.find(routeId)).toString());

        return "/route/edit";
    }

    @RequestMapping("/edit2")
    public String edit2(@RequestParam(name = "id") Long routeId, Model model)
    {
        // 在这里可以把停留点、轨迹点、问题路段等全部加载进来，用于编辑处理
        model.addAttribute("routeId", routeId);

        // 获取线路信息
        model.addAttribute("route", this.routeService.getById(routeId));
        // 获取轨迹点
        model.addAttribute("routePoints", new Gson().toJson(this.routePointService.find(routeId)).toString());
        // 获取问题路段
        model.addAttribute("troubleSegments", new Gson().toJson(this.troubleSegmentService.find(routeId)).toString());
        // 获取停留点
        model.addAttribute("stayPoints", new Gson().toJson(this.stayPointService.find(routeId)).toString());

        return "/route/edit2";
    }

    /**
     * 保存线路信息、 轨迹点、停留点、问题路段
     * @param id
     * @param name
     * @param minSpeed
     * @param maxSpeed
     * @param mileages
     * @param pointsJsonText
     * @param stayPointsJsonText
     * @param segmentsJsonText
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Result save(@RequestParam Long id,
                       @RequestParam String name,
                       @RequestParam Integer minSpeed,
                       @RequestParam Integer maxSpeed,
                       @RequestParam Integer mileages,
                       @RequestParam String pointsJsonText,
                       @RequestParam String stayPointsJsonText,
                       @RequestParam String segmentsJsonText)
    {
        Result result = new Result();
        try
        {
            // 根据线路ID获取信息
            Route route = this.routeService.getById(id);
            if (route == null) {
                return result;
            }
            // 更新线路信息
            route.setName(name);
            route.setMinSpeed(minSpeed);
            route.setMaxSpeed(maxSpeed);
            route.setMileages(mileages);

            StringBuilder signature = new StringBuilder(4096 * 10);
            signature.append(String.valueOf(minSpeed));
            signature.append(String.valueOf(maxSpeed));
            signature.append(String.valueOf(mileages));
            signature.append(pointsJsonText);
            signature.append(stayPointsJsonText);
            signature.append(segmentsJsonText);
            String fingerPrint = MD5.encode(signature.toString());
            route.setFingerPrint(fingerPrint);

            this.routeService.update(route);

            // 添加原轨迹点
            List<RoutePoint> points = null;
            if (!StringUtils.isEmpty(pointsJsonText))
            {
                points = new Gson().fromJson(pointsJsonText, new TypeToken<List<RoutePoint>>() {}.getType());
            }
            if (points != null) {
                for (RoutePoint point : points)
                {
                    point.setLatitude(point.getLat());
                    point.setLongitude(point.getLng());
                    point.setRouteId(id);
                }
                routePointService.batchSave(route, points);
            }

            // 添加停留点
            List<StayPoint> stayPoints = null;
            if (!StringUtils.isEmpty(stayPointsJsonText)) {
                stayPoints = new Gson().fromJson(stayPointsJsonText, new TypeToken<List<StayPoint>>(){}.getType());
            }
            if (stayPoints != null) {
                for (StayPoint stayPoint : stayPoints) {
                    stayPoint.setId(null);
                    stayPoint.setRouteid(id);
                }
                this.stayPointService.save(route, stayPoints);
            }

            // 添加问题路段
            List<TroubleSegment> troubleSegments = null;
            if (!StringUtils.isEmpty(segmentsJsonText)) {
                troubleSegments = new Gson().fromJson(segmentsJsonText, new TypeToken<List<TroubleSegment>>() {}.getType());
            }
            if (troubleSegments != null) {
                for (TroubleSegment segment : troubleSegments) {
                    segment.setId(null);
                    segment.setRouteId(id);
                }
                this.troubleSegmentService.save(route, troubleSegments);
            }

            // 更新内存中的线路缓存
            RouteManager.getInstance().load(route);
        }
        catch(Exception e)
        {
            result.setError(e);
        }
        return result;
    }
}
