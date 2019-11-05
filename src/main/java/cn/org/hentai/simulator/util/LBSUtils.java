package cn.org.hentai.simulator.util;

import cn.org.hentai.simulator.entity.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by houcheng on 9/12/2019.
 */
public final class LBSUtils
{
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 2公里（千米）
     */
    private static int KILOMETER_TWO = 2000;

    /**
     * 10公里（千米）
     */
    private static int KILOMETER_TEN = 10000;

    /**
     * 计算两个位置间的直线距离
     * @param lng1 A点经度
     * @param lat1 A点纬度
     * @param lng2 B点经度
     * @param lat2 B点纬度
     * @return 距离，单位：米
     */
    public static int directDistance(double lng1, double lat1, double lng2, double lat2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = (distance * 10000) / 10000 * 1000;
        return (int)distance;
    }

    /**
     * 计算两个点间的直线距离
     * @param p1 点1
     * @param p2 点2
     * @return 直线距离（单位：米）
     */
    public static int directDistance(Position p1, Position p2)
    {
        return directDistance(p1.getLongitude(), p1.getLatitude(), p2.getLongitude(), p2.getLatitude());
    }

    /**
     * 计算两个点的直线的角度
     * @param start 开始坐标点
     * @param end 结束坐标点
     * @return 角度整型
     */
    public static int angle(Position start, Position end) {
        double angle = Math.atan2(start.getLongitude() - end.getLongitude(), start.getLatitude() - end.getLatitude())* 180 / Math.PI;
        return (int) Math.abs(angle);
    }

    /**
     * 计算轨迹所经历的总里程（单位：米）
     * @param tracks 行驶的轨迹点
     * @return 总距离（单位：米）
     */
    public static int measure(List<Position> tracks)
    {
        int meters = 0;
        if (tracks.size() <= 1) return meters;
        Position last = tracks.get(0);
        for (int i = 1; i < tracks.size(); i++)
        {
            meters += directDistance(last, tracks.get(i));
            last = tracks.get(i);
        }
        return meters;
    }

    /**
     * 点是否在多边形内
     * @param polygon 多边形的各顶点，忘了要不要封闭起来了
     * @param pt 目标点
     * @return
     */
    public static boolean isInPolygon(List<Position> polygon, Position pt)
    {
        int count = polygon.size();
        if (count == 0) return false;
        boolean c = false;

        for (int i = -1, l = count, j = l - 1; ++i < l; j = i)
        {
            Position pi = polygon.get(i);
            Position pj = polygon.get(j);
            boolean x = ((pi.getLatitude() <= pt.getLatitude() && pt.getLatitude() < pj.getLatitude()) || (pj.getLatitude() <= pt.getLatitude() && pt.getLatitude() < pi.getLatitude()))
                    && (pt.getLongitude() < (pj.getLongitude() - pi.getLongitude()) * (pt.getLatitude() - pi.getLatitude()) / (pj.getLatitude() - pi.getLatitude()) + pi.getLongitude())
                    && (c = !c);
        }

        return c;
    }

    // 距离折线最近的距离
    public static int distanceToPolyline(List<Position> polyline, Position point)
    {
        double distance = Integer.MAX_VALUE;

        for (int i = -1, l = polyline.size(), j = l - 1; ++i < l; j = i)
        {
            Position pi = polyline.get(i);
            Position pj = polyline.get(j);

            double meters = distanceToLine(point, pi, pj);
            if (Double.isNaN(meters)) return 0;
            distance = Math.min(distance, meters);
        }

        return (int) distance;
    }

    /**
     * 目标点x到y->z所组成的线段的距离
     * @param x 目标点
     * @param y 线段起点
     * @param z 线段终点
     * @return 点到线段的距离，NaN表示点就在线段上
     */
    public static double distanceToLine(Position x, Position y, Position z)
    {
        // 需要考虑点在线段上的情况

        // 1. 计算三个点所组成的三角形的各边长
        long a = directDistance(x, y);
        long b = directDistance(y, z);
        long c = directDistance(z, x);

        // 2. 通过海伦公式计算三个点所组成的三角形的面积
        long p = (a + b + c) / 2;
        double S = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        System.out.println(S);

        // 3. 通过三角形面积公式反推高，即点到线段的距离
        double d = (S * 2) / b;
        return d;
    }

    /**
     * 计算多边形的中心点（首元素好像要重复出现两次才好，好像，暂不使用）
     * @param polygon 多边形的顶点列表
     * @return 中心点位置
     */
    public static Position getCenterPoint(List<Position> polygon)
    {
        Position first = polygon.get(0), last = polygon.get(polygon.size() - 1);
        double twicearea = 0, x = 0, y = 0, f;
        Position p1, p2;
        int nptc = polygon.size();

        for (int i = 0, j = nptc - 1; i < nptc; j = i++)
        {
            p1 = polygon.get(i);
            p2 = polygon.get(j);
            f = p1.getLongitude() * p2.getLatitude() - p2.getLongitude() * p1.getLatitude();
            twicearea += f;
            x += (p1.getLongitude() + p2.getLongitude()) * f;
            y += (p1.getLatitude() + p2.getLatitude()) * f;
        }

        f = twicearea * 3;
        double lng = x / f;
        double lat = y / f;

        if (Double.isNaN(lng) || Double.isNaN(lat)) return null;
        return new Position(lng, lat);
    }
}
