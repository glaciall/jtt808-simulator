package cn.org.hentai.simulator.task;

import cn.org.hentai.simulator.entity.DrivePlan;
import cn.org.hentai.simulator.entity.Point;
import cn.org.hentai.simulator.entity.TaskInfo;
import cn.org.hentai.simulator.jtt808.JTT808Message;
import cn.org.hentai.simulator.task.runner.Executable;
import cn.org.hentai.simulator.task.runner.RunnerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matrixy when 2020/5/8.
 * 车辆行程任务
 * 注意：本类里的方法全部由LoopRunner执行，不需要额外考虑线程安全问题
 * 此类为抽象类，对实际的消息与事件处理由子类实现完成
 */
public abstract class AbstractDriveTask implements Driveable
{
    static Logger logger = LoggerFactory.getLogger(AbstractDriveTask.class);

    // 行程ID
    private long id;

    private long routeId;

    // 运行模式：调试模式，压测模式
    private String mode;

    // 车辆当前状态：就绪、启动、停车、熄火、
    private TaskState state;

    private DrivePlan drivePlan;

    private Point currentPosition;

    // 报警标志位
    private int warningFlags = 0;
    // 状态标志位，ACC开，已定位，已使用GPS定位
    private int stateFlags = (1 << 0) | (1 << 1) | (1 << 18);

    // 日志信息：在调试模式时记录下来
    private Object logs;

    // TODO: 需要把几个常用的，用于显示在表格上的属性值，整成成员属性，避免对Map的并发读写
    TaskInfo info;

    Map<String, String> parameters;

    public AbstractDriveTask(long id, long routeId)
    {
        this.id = id;
        this.routeId = routeId;
    }

    public long getId()
    {
        return this.id;
    }

    public long getRouteId()
    {
        return this.routeId;
    }

    public final int getWarningFlags()
    {
        return this.warningFlags;
    }

    public final int getStateFlags()
    {
        return this.stateFlags;
    }

    /**
     * 使用参数集settings进行初始化
     * @param settings 参数集名值对
     */
    public final void init(Map<String, String> settings, DrivePlan plan)
    {
        // 复制一份
        this.drivePlan = plan;
        this.parameters = new HashMap<>(settings.size());
        for (String key : settings.keySet())
        {
            this.parameters.put(key, settings.get(key));
        }
        this.mode = getParameter("mode");

        this.state = TaskState.idle;

        this.info = new TaskInfo()
                .withId(id)
                .withRouteId(routeId)
                .withVehicleNumber(getParameter("vehicle.number"))
                .withDeviceSn(getParameter("device.sn"))
                .withSimNumber(getParameter("device.sim"))
                .withStartTime(System.currentTimeMillis());
    }

    // 获取下一个位置信息
    public final Point getNextPoint()
    {
        return currentPosition = drivePlan.getNextPoint();
    }

    public final Point getCurrentPosition()
    {
        return currentPosition;
    }

    // HINT: 整个日志吧，而且要根据当前的模式来决定是不是真的保存下来
    public final void log(String msg)
    {
        // 日志的要素有什么？时间就调用时的时间，类型：网络类/用户交互/服务器交互/其它、数据

        if ("debug".equals(this.mode) == false) return;
        // HOWTO: 保存到什么地方好呢？
        logger.info(msg);
    }

    /**
     * 在millis毫秒后执行Exectable
     * @param executable 需要执行的任务
     * @param milliseconds 等待的毫秒
     */
    public final void executeAfter(Executable executable, int milliseconds)
    {
        RunnerManager.getInstance().execute(this, executable, milliseconds);
    }

    public final void execute(Executable executable)
    {
        executeAfter(executable, 0);
    }

    /**
     * 按照interval毫秒的间隔执行Exectable
     * @param executable 需要执行的任务
     * @param interval 间隔时间，单位毫秒
     */
    public final void executeConstantly(Executable executable, int interval)
    {
        RunnerManager.getInstance().execute(this, executable, interval, interval);
    }

    public String getParameter(String name)
    {
        return this.parameters.get(name);
    }

    public TaskState getState()
    {
        return this.state;
    }

    @Override
    public void terminate()
    {
        log("task terminated");
        this.state = TaskState.terminated;
    }

    public TaskInfo getInfo()
    {
        if (currentPosition != null)
        {
            info.setLongitude(currentPosition.getLongitude());
            info.setLatitude(currentPosition.getLatitude());
            info.setReportTime(currentPosition.getReportTime());
            info.setStateFlags(this.stateFlags);
            info.setWarningFlags(this.warningFlags);
        }
        info.setState(this.state.getName());
        return info;
    }

    public void setStateFlag(int index, boolean on)
    {
        if (on == false) this.stateFlags &= ~(1 << index);
        else this.stateFlags |= (1 << index);
    }

    public void setWarningFlag(int index, boolean on)
    {
        if (on == false) this.warningFlags &= ~(1 << index);
        else this.warningFlags |= (1 << index);
    }

    // 发送消息
    public abstract void send(JTT808Message msg);
}
