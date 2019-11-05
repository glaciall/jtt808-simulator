package cn.org.hentai.simulator.manager;

import cn.org.hentai.simulator.entity.XVehicleDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by houcheng on 2019/1/7.
 * 线程池管理器，用于IState的需要长时间执行的方法的运行
 */
public final class ThreadManager
{
    static Logger logger = LoggerFactory.getLogger(ThreadManager.class);

    static final ThreadPoolExecutor executor = new ThreadPoolExecutor(12, 12, 60, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(15000));
    static final AtomicLong sequence = new AtomicLong(1);

    static Worker[] workers = null;                                             // 工作线程
    static ConcurrentLinkedQueue<XVehicleDevice> pendings;                      // 有需要发送消息的车机

    public synchronized static void init()
    {
        if (workers != null) return;
        pendings = new ConcurrentLinkedQueue<>();
        workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker();
            workers[i].start();
        }
        new Timer().scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                // 看看有没有待处理的
                checkPendings();
            }
        }, 10, 30);
    }

    static void checkPendings()
    {
        // 尽可能最后一起上
        while (pendings.isEmpty() == false)
        {
            XVehicleDevice vehicleDevice = pendings.poll();
            int hash = Math.abs(vehicleDevice.hashCode() % workers.length);
            workers[hash].depute(vehicleDevice);
        }
        for (Worker worker : workers)
        {
            worker.awake();
        }
    }

    public static void depute(XVehicleDevice vehicleDevice)
    {
        pendings.add(vehicleDevice);
    }

    static class Worker extends Thread
    {
        static Logger logger = LoggerFactory.getLogger(Worker.class);

        LinkedList<XVehicleDevice> pendings = new LinkedList<>();

        public void depute(XVehicleDevice vehicleDevice)
        {
            synchronized (pendings)
            {
                pendings.add(vehicleDevice);
            }
        }

        public void awake()
        {
            synchronized (pendings)
            {
                pendings.notifyAll();
            }
        }

        public void run()
        {
            while (!Thread.interrupted())
            {
                while (pendings.size() > 0)
                {
                    try
                    {
                        XVehicleDevice vehicleDevice = null;
                        synchronized (pendings)
                        {
                            pendings.wait();
                            vehicleDevice = pendings.removeFirst();
                            pendings.notifyAll();
                        }

                        vehicleDevice.sendAllPendings();
                    }
                    catch(Exception e)
                    {
                        logger.error(e.toString());
                    }
                }
                try { Thread.sleep(30); } catch(Exception e) { }
            }
        }
    }


    /**
     * 设置线程池工厂名称
     */
    public static void setThreadFactory()
    {
        executor.setThreadFactory(r -> {
            Thread thread = new Thread(r);
            thread.setName("simulator-worker-" + sequence.addAndGet(1));
            return thread;
        });
    }

    public static void execute(Runnable runnable)
    {
        executor.execute(runnable);
    }
}
