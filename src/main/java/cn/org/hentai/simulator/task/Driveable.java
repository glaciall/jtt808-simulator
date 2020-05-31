package cn.org.hentai.simulator.task;

/**
 * Created by matrixy when 2020/5/8.
 */
public interface Driveable
{
    /**
     * 启动
     */
    public void startup();

    // TODO: 熄火？

    // 停车，彻底完成行程后的处理
    public void terminate();

    // TODO: 故障？
}
