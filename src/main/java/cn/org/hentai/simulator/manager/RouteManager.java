package cn.org.hentai.simulator.manager;

import cn.org.hentai.simulator.entity.*;
import cn.org.hentai.simulator.util.BeanUtils;
import cn.org.hentai.simulator.util.LBSUtils;
import cn.org.hentai.simulator.web.entity.Route;
import cn.org.hentai.simulator.web.entity.RoutePoint;
import cn.org.hentai.simulator.web.entity.StayPoint;
import cn.org.hentai.simulator.web.entity.TroubleSegment;
import cn.org.hentai.simulator.web.service.RoutePointService;
import cn.org.hentai.simulator.web.service.RouteService;
import cn.org.hentai.simulator.web.service.StayPointService;
import cn.org.hentai.simulator.web.service.TroubleSegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by houcheng on 2018/11/23.
 * 线路缓存管理
 */
public final class RouteManager
{
    private static Logger logger = LoggerFactory.getLogger(RouteManager.class);

    // 在进行停留点比较时，取样的点的数量
    static final int SCOPE = 5;
    // 停留点到线路轨迹的最长距离
    static final int MAX_DISNTANCE_TO_STAYPOINT = 2500;

    static RouteManager instance = null;

    int sequence = 1;

    ConcurrentHashMap<Long, XRoute> routes = new ConcurrentHashMap<Long, XRoute>();
    private RouteManager()
    {
        // ...
    }

    public static synchronized RouteManager getInstance()
    {
        if (null == instance) instance = new RouteManager();
        return instance;
    }

    // 初始化，加载数据库中的线路以及其问题路段、停留点等信息
    // 此方法暂时只是从文件中去加载数据
    public void init()
    {
        logger.info("初始化线路开始...");
        if (routes.size() > 0) return;
        RouteService routeService = null;
        try
        {
            routeService = BeanUtils.create(RouteService.class);
            List<Route> routes = routeService.list();
            for (Route route : routes)
            {
                load(route);
            }
            logger.info("初始化线路结束...");
        }
        catch(Exception ex)
        {
            logger.error("初始化线路异常！", ex);
        }
        finally
        {
            try { BeanUtils.destroy(routeService); } catch(Exception e) {}
        }
    }

    // 将routeId指定的线路信息加载到缓存中来
    // 此方法留予Controller层面调用，当用户在页面上创建或修改了线路信息时，调用此方法将线路信息加载到缓存中来
    public void load(Long routeId)
    {
        Route route = null;
        RouteService routeService = null;
        try
        {
            routeService = BeanUtils.create(RouteService.class);
            route = routeService.getById(routeId);
            if (null == route) throw new RuntimeException("no such route: " + routeId);

            load(route);
        }
        catch(Exception ex)
        {
            logger.error("", ex);
        }
        finally
        {
            try { BeanUtils.destroy(routeService); } catch(Exception e) { }
        }
    }

    public void load(Route route)
    {
        XRoute xr = new XRoute();
        RoutePointService pointService = null;
        StayPointService stayPointService = null;
        TroubleSegmentService segmentService = null;
        try
        {
            pointService = BeanUtils.create(RoutePointService.class);
            stayPointService = BeanUtils.create(StayPointService.class);
            segmentService = BeanUtils.create(TroubleSegmentService.class);

            xr.setMinSpeed(route.getMinSpeed());
            xr.setMaxSpeed(route.getMaxSpeed());
            xr.setId(route.getId());
            xr.setFingerPrint(route.getFingerPrint());

            // 轨迹点
            List<Position> points = new LinkedList();
            for (RoutePoint rp : pointService.find(route.getId()))
            {
                Position p = new Position(rp.getLongitude(), rp.getLatitude());
                points.add(p);
            }
            xr.setPositionList(points);

            // 停留点
            LinkedList<XStayPoint> stayPoints = new LinkedList();
            for (StayPoint sp : stayPointService.find(route.getId()))
            {
                XStayPoint xsp = new XStayPoint(sp.getLongitude(), sp.getLatitude(), sp.getMinTime(), sp.getMaxTime(), sp.getRatio());
                stayPoints.add(xsp);
            }
            xr.setVehicleStayPointList(stayPoints);

            // 问题路段
            LinkedList<XTroubleSegment> segments = new LinkedList();
            for (TroubleSegment segment : segmentService.find(route.getId()))
            {
                XTroubleSegment xts = new XTroubleSegment(segment.getStartIndex(), segment.getEndIndex(), segment.getEventCode(), segment.getRatio());
                segments.add(xts);
            }
            xr.setTroubleSegmentList(segments);

            routes.put(route.getId(), xr);
        }
        catch(Exception ex)
        {
            logger.error("route load failed", ex);
        }
        finally
        {
            try { BeanUtils.destroy(pointService); } catch(Exception e) { }
            try { BeanUtils.destroy(stayPointService); } catch(Exception e) { }
            try { BeanUtils.destroy(segmentService); } catch(Exception e) { }
        }
    }

    // 删除id为routeId的线路
    public void remove(Long routeId)
    {
        routes.remove(routeId);
    }

    public DrivePlan generate(Long routeId, Date startTime)
    {
        LinkedList<Point> points = new LinkedList();
        XRoute route = routes.get(routeId);
        List<Position> positionList = route.getPositionList();

        // 行驶速度随机化、轨迹点随机化、确定每一个点的到达时间
        points = routeRandomize(positionList, route.getMinSpeed(), route.getMaxSpeed());

        // 产生报警事件
        // generateEvents(points, route.getTroubleSegmentList());

        // 生成停留点，并且修正上报时间
        // TODO: 同时完善安全事件信息
        generateStayPoints(startTime, points, route.getVehicleStayPointList());

        DrivePlan plan = new DrivePlan();
        plan.setRoutePoints(points);

        return plan;
    }

    // TODO: 创建报警事件，暂时先屏蔽掉
    private void generateEvents(LinkedList<Point> points, List<XTroubleSegment> troubleSegmentList)
    {
        if (troubleSegmentList.size() == 0) return;

        int i = -1;
        int eindex = 0;
        for (Point point : points)
        {
            i += 1;

            // 循环问题路段的轨迹点
            for (int k = eindex; k < troubleSegmentList.size(); k++)
            {
                XTroubleSegment segment = troubleSegmentList.get(k);
                if (segment == null) continue;

                boolean match = i >= segment.getStartIndex() && i <= segment.getEndIndex();
                if (!match) continue;

                // 如果轨迹点所在的索引处于某一问题路段内，则根据概率记录安全事件类型
                if (Math.random() * 100 < segment.getRatio())
                {
                    // 获取此问题路段的安全事件
                    // TODO: point.setEvent(segment.getEvent());
                    eindex += 1;
                }
            }
        }
    }

    // 创建停留点
    private void generateStayPoints(Date startTime, LinkedList<Point> points, List<XStayPoint> vehicleStayPointList)
    {
        int sindex = 0;
        for (int i = 0; i < points.size(); )
        {
            int positionSize = points.size();
            Point position = points.get(i);
            if (vehicleStayPointList.size() == 0) break;
            if (sindex >= vehicleStayPointList.size()) break;

            XStayPoint stayPoint = vehicleStayPointList.get(sindex);

            // 此处应该计算点到线段间的距离
            // TODO: 停留点的速度应该很低很低，延长发送时长到15秒吧
            int distance = LBSUtils.directDistance(position.getLongitude(), position.getLatitude(), stayPoint.getLongitude(), stayPoint.getLatitude());
            if (distance > MAX_DISNTANCE_TO_STAYPOINT)
            {
                // 如果当前点距离停留点大于150米，则不设置停留点
                // 重新将此停留点添加到列表中
                // vehicleStayPointList.addFirst(stayPoint);
                i += 1;
                continue;
            }

            // 距离停留点最近的轨迹点
            boolean isFound = true;
            for (int k = Math.max(0, i - 3), s = i + SCOPE; k < s && k < positionSize; k++)
            {
                Point comparePosition = points.get(k);
                // TODO: 此处应该计算点到线段的距离，可使用海伦公式计算三角形的面积，然后再通过（面积 = 底 * 高 / 2）反推高度
                // 因为当前的停留点，可能会因为车辆时速过快，而超过了MAX_DISTANCE_TO_STAYPOINT
                int compareDistance = LBSUtils.directDistance(comparePosition.getLongitude(), comparePosition.getLatitude(), stayPoint.getLongitude(), stayPoint.getLatitude());
                if (compareDistance < distance)
                {
                    isFound = false;
                    break;
                }
            }

            if (isFound == false)
            {
                // vehicleStayPointList.addFirst(stayPoint);
                i += 1;
                continue;
            }
            sindex += 1;

            // 由概率决定要不要停留
            double rand = Math.random() * 100;
            if (rand > stayPoint.getRatio())
            {
                i += 1;
                continue;
            }

            // 设置N个停留点
            // 从最低时长与最高时长中随机一个时长
            int appropriateTime = (int) Math.round(Math.random() * (stayPoint.getMaxTime() - stayPoint.getMinTime()) + stayPoint.getMinTime());

            // 统一在经度的第5位设置随机数
            Double newLng = stayPoint.getLongitude();
            Double newLat = stayPoint.getLatitude();

            // 循环停留时长
            for (int m = 0, e = appropriateTime * 60 * 1000; m < e; )
            {
                double r1 = (Math.random() * 20 - 10) * Math.pow(10, -5);
                double r2 = (Math.random() * 20 - 10) * Math.pow(10, -5);
                // TODO: 这个15000需要跟下一个循环体里的15000同样的来源
                m += 15000;
                Point newPoint = new Point();
                newPoint.setLongitude(newLng + r1);
                newPoint.setLatitude(newLat + r2);
                newPoint.setSpeed(0);
                newPoint.setStay(true);
                points.add(i, newPoint);
                i += 1;
            }
            // System.out.println(String.format("StayPoint @ [%.6f x %.6f]", newLng, newLat));
        }
        // System.out.println("***************************************************************************************************");

        // 修正轨迹点的上报时间
        long time = startTime.getTime();
        for (Point point : points)
        {
            // TODO: 这里的时间间隔，需要由用户设置值来决定
            time += 5000;
            if (point.isStay())
            {
                time += 15000;
            }
            point.setReportTime(time);
        }
    }

    // 轨迹点随机化
    private LinkedList<Point> routeRandomize(List<Position> positionList, int minSpeed, int maxSpeed)
    {
        // 每SAMPLING_RATIO个轨迹点生成一个随机速度点，然后再在之间进行更小范围的随机化
        final int SAMPLING_RATIO = 60;

        // 生成时速随机区间，速度为：米/秒。
        int lastspeedCurve = 0;
        float[] speedCurve = new float[positionList.size() / SAMPLING_RATIO + 10];
        for (int i = 0; i < speedCurve.length; i++) speedCurve[i] = (float)(Math.random() * (maxSpeed - minSpeed) + minSpeed) * 1000 / 3600;

        LinkedList<Point> points = new LinkedList();
        for (int i = 0, l = positionList.size(); i < l; )
        {
            Position current = positionList.get(i);
            Position next = i < l - 1 ? positionList.get(i + 1) : null;

            // TODO: 速度应该从0开始，到0结束
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            // 计算在这个节点时它应该取的速度值
            float speed = speedCurve[i / SAMPLING_RATIO];
            float nextSpeed = speedCurve[i / SAMPLING_RATIO + 1];
            float diff = nextSpeed - speed;
            // p1 : [  0, sp ]
            // p2 : [ 60, ns ]
            // x = i % 60
            // y = kx
            float k = diff / SAMPLING_RATIO;
            float x = i % SAMPLING_RATIO;
            diff = (diff / SAMPLING_RATIO) * 2;
            // 在[+diff, -diff]之间取随机值
            // System.out.println(String.format("speed: %.2f, nextSpeed: %.2f", speed, nextSpeed));
            // System.out.println(String.format("i = %d, k = %.6f, x = %.0f", i, k, x));
            // System.out.println("*******************************");
            float y = k * x + (float)(diff - Math.random() * diff * 2);
            speed = y + speed;
            if (Float.isNaN(speed) || Float.isInfinite(speed)) speed = speedCurve[i / SAMPLING_RATIO];
            // else System.out.println(String.format("%.2f", speed));

            // 确定一下速度曲线

            // 以当前点为起点，以speed的速度，行驶5秒后，应该到哪里了。。

            // 下一个时间点，应该到达多远之后
            int distanceToNext = (int)(speed * 5);
            LinkedList<Position> partial = new LinkedList<Position>();
            partial.add(current);
            int d = 0;
            for (i = i + 1; i < l; i++, d++)
            {
                partial.add(positionList.get(i));
                int meters = LBSUtils.measure(partial);
                if (meters < distanceToNext) continue;

                // Log.debug(String.format("skiped: %d, at speed: %.1f", d, speed));

                // 确定了到达第i点才是应该到的地方了，接下来
                // 1. 从超过的点起，到下一个点之间，随机取一个点
                Position from = positionList.get(i - 1), to = positionList.get(i);
                Position pt = randomizeNextPosition(from, to);
                Point p = new Point();
                p.setLongitude(pt.getLongitude());
                p.setLatitude(pt.getLatitude());
                p.setSpeed((float)(speed * 3600 / 1000));
                // TODO: 计算方向，正北为0，顺时针方向
                points.add(p);
                break;
            }
        }
        return points;
    }

    // 随机化轨迹点，不完全按照即定的轨迹点来行进
    private Position randomizeNextPosition(Position p1, Position p2)
    {
        final int M = 1000000;

        // 计算斜率
        if (p1.getLongitude().doubleValue() == p2.getLongitude().doubleValue())
        {
            // Log.debug("因为k不存在，所以取了p2");
            return p2;
        }
        double k = (p1.getLatitude() * M - p2.getLatitude() * M) / (p1.getLongitude() * M - p2.getLongitude() * M);
        // 计算角度
        double a = Math.atan(k);
        // 计算距离
        double r = Math.sqrt(Math.pow((p1.getLongitude() * M - p2.getLongitude() * M), 2) + Math.pow((p1.getLatitude() * M - p2.getLatitude() * M), 2));
        // 取20%~80%的r长度，重新计算p1到p2的坐标
        // 原来是p1通过a角度经过r的长度到达p2，现在r缩减到20%~80%，重新计算落点
        // r = ((Math.random() * 6) + 2) * r / 10;
        // r在[+10%, -10%]之间随机取值
        // System.out.println(String.format("r = %.6f", r));
        r = r + (r / 5 - Math.random() * (r / 2));
        // System.out.println(String.format("r = %.6f", r));
        // 根据余弦公式计算x坐标
        double x = Math.cos(a) * r;
        // 通过勾股定律计算y坐标
        double y = Math.sqrt(r * r - x * x);
        // System.out.println(String.format("k = %.6f, a = %.6f", k, a));
        // System.out.println(String.format("x = %.6f, y = %.6f", x, y));

        // if (k < 0) y = 0 - y;
        if (p2.getLatitude() < p1.getLatitude() && y > 0) y = 0 - y;
        if (p2.getLongitude() < p1.getLongitude() && x > 0) x = 0 - x;

        // System.out.println(String.format("x = %.6f, y = %.6f", x, y));

        x = x / M + p1.getLongitude();
        y = y / M + p1.getLatitude();

        if (Double.isNaN(x) || Double.isNaN(y))
        {
            // Log.debug("因为NAN而使用了p2");
            return p2;
        }

        return new Position(x, y);
    }
}
