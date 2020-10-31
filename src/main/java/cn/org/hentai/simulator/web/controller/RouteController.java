package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.entity.DrivePlan;
import cn.org.hentai.simulator.entity.Point;
import cn.org.hentai.simulator.manager.RouteManager;
import cn.org.hentai.simulator.util.MD5;
import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.entity.RoutePoint;
import cn.org.hentai.simulator.web.entity.StayPoint;
import cn.org.hentai.simulator.web.entity.TroubleSegment;
import cn.org.hentai.simulator.web.service.RoutePointService;
import cn.org.hentai.simulator.web.service.RouteService;
import cn.org.hentai.simulator.web.service.StayPointService;
import cn.org.hentai.simulator.web.service.TroubleSegmentService;
import cn.org.hentai.simulator.web.vo.Page;
import cn.org.hentai.simulator.web.vo.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by houcheng when 2018/11/25.
 * 线路控制器
 */
@Controller
@RequestMapping("/route")
public class RouteController
{
    @Autowired
    RouteService routeService;

    @Autowired
    private RoutePointService routePointService;

    @Autowired
    private StayPointService stayPointService;

    @Autowired
    private TroubleSegmentService troubleSegmentService;

    @Value("${map.baidu.key}")
    String baiduMapKey;

    @RequestMapping("/index")
    public String index()
    {
        return "routes";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "20") int pageSize)
    {
        Result result = new Result();
        try
        {
            Page<Route> routes = routeService.find(null, pageIndex, pageSize);

            result.setData(routes);
        } catch (Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }

    /**
     * 创建线路并跳转到编辑页面
     *
     * @return
     */
    @RequestMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("baiduMapKey", baiduMapKey);
        return "route-create";
    }

    /**
     * 保存线路信息、 轨迹点、停留点、问题路段
     *
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
    public Result save(@RequestParam String name,
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
            Route route = new Route();
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

            routeService.create(route);

            long id = route.getId();

            // 添加原轨迹点
            List<RoutePoint> points = null;
            if (!StringUtils.isEmpty(pointsJsonText))
            {
                points = new Gson().fromJson(pointsJsonText, new TypeToken<List<RoutePoint>>()
                {
                }.getType());
            }
            if (points != null)
            {
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
            if (!StringUtils.isEmpty(stayPointsJsonText))
            {
                stayPoints = new Gson().fromJson(stayPointsJsonText, new TypeToken<List<StayPoint>>()
                {
                }.getType());
            }
            if (stayPoints != null)
            {
                for (StayPoint stayPoint : stayPoints)
                {
                    stayPoint.setId(null);
                    stayPoint.setRouteid(id);
                }
                stayPointService.save(route, stayPoints);
            }

            // 添加问题路段
            List<TroubleSegment> troubleSegments = null;
            if (!StringUtils.isEmpty(segmentsJsonText))
            {
                troubleSegments = new Gson().fromJson(segmentsJsonText, new TypeToken<List<TroubleSegment>>()
                {
                }.getType());
            }
            if (troubleSegments != null)
            {
                for (TroubleSegment segment : troubleSegments)
                {
                    segment.setId(null);
                    segment.setRouteId(id);
                }
                troubleSegmentService.save(route, troubleSegments);
            }

            // 更新内存中的线路缓存
            RouteManager.getInstance().load(route);
        } catch (Exception e)
        {
            result.setError(e);
        }
        return result;
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Result remove(@RequestParam Long id)
    {
        Result result = new Result();
        try
        {
            routeService.removeById(id);
            routePointService.removeByRouteId(id);
            stayPointService.removeByRouteId(id);
        }
        catch(Exception ex)
        {
            result.setError(ex);
        }
        return result;
    }
}
