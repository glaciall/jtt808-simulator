package cn.org.hentai.simulator.web.entity;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by matrixy on 2018-04-23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface Mapper
{
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";

}
