package cn.org.hentai.simulator.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by matrixy on 2019/8/27.
 */
public final class DateUtils
{
    /**
     * 获取今日，时分秒全部置零
     * @return
     */
    public static Date today()
    {
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        return today;
    }

    /**
     * 获取昨天的日期
     * @return 昨天
     */
    public static Date yesterday()
    {
        return before(today(), 1);
    }

    /**
     * 获取明天的日期
     * @return 明天
     */
    public static Date tomorrow()
    {
        return after(today(), 1);
    }

    /**
     * 使用yyyy-MM-dd格式化给定的日期
     * @param date 待格式化的日期
     * @return 格式化后的字符串
     */
    public static String format(Date date)
    {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 使用指定的格式化字符串格式日期
     * @param date 待格式化的日期
     * @param format 格式字符串
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format)
    {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 使用指定的格式化字符串对日期字符串进行解析
     * @param dateStr 日期字符串
     * @param format 格式化字符串
     * @return 日期
     */
    public static Date parse(String dateStr, String format)
    {
        try
        {
            return new SimpleDateFormat(format).parse(dateStr);
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 使用yyyy-MM-dd对日期字符串进行解析
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parse(String dateStr)
    {
        return parse(dateStr, "yyyy-MM-dd");
    }

    /**
     * 获取date之后的第days天的时间
     * @param date 开始日期
     * @param days 天数
     * @return 日期
     */
    public static Date after(Date date, int days)
    {
        return new Date(date.getTime() + 1000L * 60 * 60 * 24 * days);
    }

    /**
     * 获取当天之后的的第days天的日期
     * @param days 天数
     * @return 日期
     */
    public static Date after(int days)
    {
        return after(today(), days);
    }

    /**
     * 获取date之前days天的日期
     * @param date 日期
     * @param days 天数
     * @return 日期
     */
    public static Date before(Date date, int days)
    {
        return new Date(date.getTime() - 1000L * 60 * 60 * 24 * days);
    }

    /**
     * 获取当天之前days天的日期
     * @param days 天数
     * @return 日期
     */
    public static Date before(int days)
    {
        return before(today(), days);
    }

    /**
     * 输入某年某月，获取这个月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取指定日期往前months个月的月份第一天
     * @param date
     * @param months
     * @return
     */
    public static Date beforeMonth(Date date , int months)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH , -months);
        Date rDate = cal.getTime();
        return parse(format(rDate ,"yyyy-MM-01"));
    }

    public static int hour(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static byte compare(Date date ,Date start ,Date end)
    {
        if(start == null || end == null)return 0;
        if(date.compareTo(start) == -1)return 0;
        if(end.compareTo(date) == -1)return 0;
        return 1;
    }

    /**
     * 比较时间过去了多久
     * @param time
     * @return
     */
    public static String getShortTime(long time) {
        String shortString = null;
        long now = Calendar.getInstance().getTimeInMillis();

        long delTime = (now - time) / 1000;
        if (delTime > 365 * 24 * 60 * 60) {
            shortString = (int) (delTime / (365 * 24 * 60 * 60)) + "年前";
        } else if (delTime > 24 * 60 * 60 * 7) {
            shortString = (int) (delTime / (24 * 60 * 60 * 7)) + "周前";
        } else if (delTime > 24 * 60 * 60) {
            shortString = (int) (delTime / (24 * 60 * 60)) + "天前";
        } else if (delTime > 60 * 60) {
            shortString = (int) (delTime / (60 * 60)) + "小时前";
        } else if (delTime > 60) {
            shortString = (int) (delTime / (60)) + "分前";
        } else {
            // 1分钟内
            shortString = "刚刚";
        }
        return shortString;
    }

    /**
     * 获取一段时间内的月份第一天的列表
     * @param minDate 开始日期 2019-01
     * @param maxDate 结束日期 2019-06
     * @return 2019-01-01,2019-02-01,2019-03-01,2019-04-01,2019-05-01,2019-06-01
     * @throws Exception
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); //格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()) + "-01");
            curr.add(Calendar.MONTH, 1);
        }
        min = null;max = null;curr = null;
        return result;
    }


    /**
     * 获取某一段时间内的日期列表
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> findDaysBetween(Date startDate, Date endDate)
    {
        List result = new ArrayList();
        result.add(startDate);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(startDate);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endDate);
        // 测试此日期是否在指定日期之后
        while (endDate.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            result.add(calBegin.getTime());
        }
        return result;
    }



    public static void main(String[] args) throws Exception {
        String minDate = "2018-05-12";
        String maxDate = "2018-05-31";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = format.parse(minDate);
        Date endDate = format.parse(maxDate);
        List<Date> list = findDaysBetween(startDate, endDate);
        for (Date date : list) {
            String dateStr =format.format(date);
            System.out.println(dateStr);
        }

    }
}
