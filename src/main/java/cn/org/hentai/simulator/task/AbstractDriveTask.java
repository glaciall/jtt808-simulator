package cn.org.hentai.simulator.task;

import cn.org.hentai.simulator.entity.DeviceInfo;
import cn.org.hentai.simulator.entity.DrivePlan;
import cn.org.hentai.simulator.entity.Point;
import cn.org.hentai.simulator.jtt808.JTT808Message;
import cn.org.hentai.simulator.task.event.EventDispatcher;
import cn.org.hentai.simulator.task.eventloop.Executable;
import cn.org.hentai.simulator.task.eventloop.RunnerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by matrixy on 2020/5/8.
 * 车辆行程任务
 * 注意：本类里的方法全部由LoopRunner执行，不需要额外考虑线程安全问题
 * 此类为抽象类，对实际的消息与事件处理由子类实现完成
 */
public abstract class AbstractDriveTask implements Driveable
{
    static Logger logger = LoggerFactory.getLogger(AbstractDriveTask.class);

    // 行程ID
    private Long id;

    // 运行模式：调试模式，压测模式
    private String mode;

    // 车辆当前状态：就绪、启动、停车、熄火、
    private String state;

    // 车辆启动时间
    private Date startTime;

    // 行驶线路轨迹点信息
    private LinkedList<Point> routePoints;

    // netty通道ID，用于发送消息
    private String channelId;

    private DrivePlan drivePlan;

    // 日志信息：在调试模式时记录下来
    private Object logs;

    Map<String, String> parameters;

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
    }

    // 获取下一个位置信息
    public final Point getNextPoint()
    {
        return drivePlan.getNextPoint();
    }

    // HINT: 整个日志吧，而且要根据当前的模式来决定是不是真的保存下来
    public final void log(String msg)
    {
        // 日志的要素有什么？时间就调用时的时间，类型：网络类/用户交互/服务器交互/其它、数据

        if ("debug".equals(this.mode) == false) return;
        // HOWTO: 保存到什么地方好呢？
        logger.debug(msg);
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

    // 启动车辆行驶行程
    @Override
    public abstract void startup();

    // 发送消息
    public abstract void send(JTT808Message msg);
}
