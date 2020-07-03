package cn.org.hentai.simulator.task.event;

import cn.org.hentai.simulator.task.AbstractDriveTask;
import cn.org.hentai.simulator.task.log.LogType;
import cn.org.hentai.simulator.task.runner.RunnerManager;
import cn.org.hentai.simulator.task.runner.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by matrixy when 2020/5/10.
 * 本类负责将AbstractDriveTask所触发的事件，分发到各成员方法上
 */
public final class EventDispatcher
{
    static Logger logger = LoggerFactory.getLogger(EventDispatcher.class);

    private static final EventDispatcher instance = new EventDispatcher();
    ConcurrentHashMap<String, Method> listenerMap;

    private EventDispatcher()
    {
        listenerMap = new ConcurrentHashMap<>(128);
    }

    // 事件委托
    public void dispatch(AbstractDriveTask driveTask, String tag, String attachment, final Object data)
    {
        try
        {
            attachment = attachment == null ? "" : attachment;

            String className = driveTask.getClass().getName();
            String mName = className + ":::" + tag + ":::" + attachment;
            Method method = listenerMap.get(mName);
            if (method == null)
            {
                // TODO: 是否有必要收集，以及是否应该交由LoopRunner去执行？
                logger.error("no event handler for: {}:::{}", tag, attachment);
                return;
            }
            Method gMethod = listenerMap.get(className + ":::" + tag + ":::");

            // TODO: 暂时只有一个参数或没有参数，后面再想办法做参数类型匹配，按需赋值，就跟spring一样

            RunnerManager.getInstance().execute(driveTask, new Executable()
            {
                @Override
                public void execute(AbstractDriveTask driveTask)
                {
                    Object[] args = new Object[method.getParameterCount()];
                    if (args.length == 1) args[0] = data;

                    try
                    {
                        // 触发一下message_received事件的回调
                        if (gMethod != null && gMethod.equals(method) == false)
                        {
                            gMethod.invoke(driveTask, args);
                        }
                    }
                    catch(Exception e) { e.printStackTrace(); }

                    try { method.invoke(driveTask, args); } catch(Exception e) { e.printStackTrace(); }
                }
            });
        }
        catch(Exception ex)
        {
            // throw new RuntimeException(ex);
            logger.error("event dispatch failed", ex);
        }
    }

    private void register0(AbstractDriveTask driveTask)
    {
        String className = driveTask.getClass().getName();
        if (listenerMap.containsKey(className)) return;

        Method[] methods = driveTask.getClass().getMethods();
        for (Method m : methods)
        {
            Listen anno = m.getAnnotation(Listen.class);
            if (anno == null) continue;
            listenerMap.put(className + ":::" + anno.when() + ":::" + anno.attachment(), m);
        }
    }

    public static void register(AbstractDriveTask driveTask)
    {
        instance.register0(driveTask);
    }

    public static void init()
    {
        // ...
    }

    public static EventDispatcher getInstance()
    {
        return instance;
    }
}
