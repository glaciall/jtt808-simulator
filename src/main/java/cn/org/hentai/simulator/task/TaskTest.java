package cn.org.hentai.simulator.task;

import cn.org.hentai.simulator.task.event.EventDispatcher;
import cn.org.hentai.simulator.task.runner.RunnerManager;
import cn.org.hentai.simulator.task.net.ConnectionPool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matrixy when 2020/5/10.
 */
public class TaskTest
{
    public static void main(String[] args) throws Exception
    {
        RunnerManager.init();
        EventDispatcher.init();
        ConnectionPool.init();

        Map<String, String> settings = new HashMap()
        {
            {
                put("server.address", "localhost");
                put("server.port", "20021");
                put("mode", "debug");
            }
        };

        final int CONCURRENT = 100;
        AbstractDriveTask[] tasks = new SimpleDriveTask[CONCURRENT];
        for (int i = 0; i < CONCURRENT; i++)
        {
            settings.put("vehicle.number", String.format("京%06d", i));
            settings.put("device.sn", String.format("%07d", i));
            settings.put("device.sim", String.format("0138%08d", i));

            tasks[i] = new SimpleDriveTask();
            tasks[i].init(settings, null);
            tasks[i].startup();
        }

        System.err.println(CONCURRENT + " drive task started...");

        System.in.read();
    }
}
