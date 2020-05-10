package cn.org.hentai.simulator.task;

import cn.org.hentai.simulator.task.event.EventDispatcher;
import cn.org.hentai.simulator.task.eventloop.RunnerManager;
import cn.org.hentai.simulator.task.net.ConnectionPool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matrixy on 2020/5/10.
 */
public class TaskTest
{
    public static void main(String[] args) throws Exception
    {
        RunnerManager.init();
        EventDispatcher.init();
        ConnectionPool.init();

        Map<String, Object> settings = new HashMap()
        {
            {
                put("server.address", "localhost");
                put("server.port", 20021);
                put("mode", "debug");
            }
        };

        AbstractDriveTask task = new SimpleDriveTask();
        task.init(settings);
        task.startup();

        System.in.read();
    }
}
