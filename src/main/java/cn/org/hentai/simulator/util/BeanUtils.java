package cn.org.hentai.simulator.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by Expect when 2018/1/25.
 */
public final class BeanUtils
{
    private static BeanFactory beanFactory;

    public static void init(ApplicationContext context)
    {
        BeanUtils.beanFactory = context;
    }

    public static <T> T create(Class serviceClass)
    {
        return (T)beanFactory.getBean(serviceClass);
    }

    public static void destroy(Object bean)
    {
        // ...
    }

    public static void free(Object bean)
    {
        // ...
    }
}
