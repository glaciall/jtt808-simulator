package cn.org.hentai.simulator.entity;

import cn.org.hentai.simulator.manager.RouteManager;
import cn.org.hentai.simulator.state.ST10Init;
import cn.org.hentai.simulator.state.StateRunner;
import cn.org.hentai.simulator.util.BeanUtils;
import cn.org.hentai.simulator.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by houcheng on 2018/11/13.
 */
public class Task extends StateRunner
{
    private static Logger logger = LoggerFactory.getLogger(RouteManager.class);

    private Long id;
    private Long scheduleTaskId;
    private Long routeId;
    private Long vehicleId;
    private XDriver driver;
    private XVehicleDevice vehicleDevice;
    private Date startTime;
    private Point currentPosition;

    private LinkedList<Point> tracks;
    private LinkedList<XEvent> events;
    private Point nextPosition;
    private XEvent nextEvent;

    private int eventCount = 0;
    private int eventSendCount = 0;

    public Task(Long scheduleTaskId, Long routeId, Long vehicleId, String deviceSn, String sim, String driverPhoto, Date startTime)
    {
        this.scheduleTaskId = scheduleTaskId;
        this.routeId = routeId;
        this.vehicleId = vehicleId;
        this.driver = new XDriver(driverPhoto);
        this.vehicleDevice = new XVehicleDevice(deviceSn, sim);
        this.vehicleDevice.setDriver(this.driver);
        this.startTime = startTime;
        this.setState(new ST10Init());

        this.id = IdGenerator.next();
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///// 步骤开始
    //////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 1、初始化准备工作
     */
    public void init()
    {
        try
        {
            // 生成新的轨迹点，以便线路分析
            DrivePlan plan = RouteManager.getInstance().generate(this.routeId, this.startTime);

            this.tracks = plan.getRoutePoints();
            this.events = new LinkedList(plan.getEvents());

            if (this.tracks.size() > 0) this.nextPosition = this.tracks.removeFirst();
            if (this.events.size() > 0) this.nextEvent = this.events.removeFirst();
            this.eventCount = plan.getEvents().size();
        }
        catch(Exception e)
        {
            logger.error("task-init", e);
        }
    }

    /**
     * 2、建立连接
     * <p>
     * 连接到服务器
     */
    public void connect()
    {
        this.vehicleDevice.connect();
    }

    // 测试并读取网络数据
    public void test()
    {
        this.vehicleDevice.receive(this);
    }

    /**
     * 3、车机鉴权
     * <p>
     * 服务器注册/鉴权
     */
    public void deviceAuthenticate()
    {
        this.vehicleDevice.authenticate();
    }

    /**
     * 4、驾驶员认证
     * <p>
     * 驾驶员身份认证
     */
    public void driverAuthenticate()
    {
        this.vehicleDevice.driverAuthenticate();
    }


    /**
     * 5、行驶数据上报
     * 5-1、发送位置信息
     *
     * @param position 当前的位置
     * @return 当前的位置
     * @throws Exception
     */
    public Point sendTrack(Point position) throws Exception
    {
        return this.vehicleDevice.sendTrack(position);
    }

    public XEvent getPendingEvent(long now)
    {
        XEvent event = null;
        if (null == nextEvent) return null;
        if (nextEvent.getReportTime() <= now)
        {
            event = nextEvent;
            nextEvent = this.events.removeFirst();
        }
        return event;
    }

    public void sendEvent(XEvent event)
    {
        try
        {
            this.vehicleDevice.sendEvent(event);
        }
        catch(Exception ex)
        {
            logger.error("发送事件出错", ex);
        }
        this.eventSendCount += 1;
    }

    public boolean isSendingEvents()
    {
        System.err.println(String.format("%d <-----> %d", eventSendCount, eventCount));
        return eventSendCount != eventCount;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///// 步骤结束
    //////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean hasTracks()
    {
        return this.tracks.size() > 0;
    }

    public Point nextPosition()
    {
        if (null == nextPosition) return null;

        // 如果下一点已经未到达时间，直接返回，否则取一个新的
        if (nextPosition.getReportTime() > System.currentTimeMillis()) return nextPosition;
        else
        {
            if (this.tracks.size() > 0) nextPosition = this.tracks.removeFirst();
            else nextPosition = null;
        }
        return nextPosition;
    }

    public Point getCurrentPosition()
    {
        return currentPosition;
    }

    public Task setCurrentPosition(Point currentPosition)
    {
        this.currentPosition = currentPosition;
        return this;
    }


    public XVehicleDevice getVehicleDevice()
    {
        return vehicleDevice;
    }

    public void setVehicleDevice(XVehicleDevice vehicleDevice)
    {
        this.vehicleDevice = vehicleDevice;
    }

    public Task withVehicleDevice(XVehicleDevice vehicleDevice)
    {
        this.setVehicleDevice(vehicleDevice);
        return this;
    }

    public Long getScheduleTaskId()
    {
        return scheduleTaskId;
    }

    public Task setScheduleTaskId(Long scheduleTaskId)
    {
        this.scheduleTaskId = scheduleTaskId;
        return this;
    }

    public Long getRouteId()
    {
        return routeId;
    }

    public Task setRouteId(Long routeId)
    {
        this.routeId = routeId;
        return this;
    }

    public XDriver getDriver()
    {
        return driver;
    }

    public void setDriver(XDriver driver)
    {
        this.driver = driver;
    }

    public Task withDriver(XDriver driver)
    {
        this.setDriver(driver);
        return this;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public Task setStartTime(Date startTime)
    {
        this.startTime = startTime;
        return this;
    }

    public Long getId()
    {
        return id;
    }

    public Task setId(Long id)
    {
        this.id = id;
        return this;
    }

    public List<Point> getTracks()
    {
        List<Point> points = new ArrayList(this.tracks.size());
        Collections.copy(points, this.tracks);
        return points;
    }

    public Long getVehicleId()
    {
        return vehicleId;
    }

    public Task setVehicleId(Long vehicleId)
    {
        this.vehicleId = vehicleId;
        return this;
    }

    public void release()
    {
        this.vehicleDevice.release();
        this.vehicleDevice = null;
        this.driver = null;
    }
}
