package cn.org.hentai.simulator.task.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by matrixy when 2020/5/10.
 * 将某方法标记为事件监听回调
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listen
{
    // 事件名称
    public EventEnum when();

    // 事件附件，用于进一步分开
    public String attachment() default "";
}