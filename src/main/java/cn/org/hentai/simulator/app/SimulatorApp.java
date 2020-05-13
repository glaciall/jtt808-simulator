package cn.org.hentai.simulator.app;

import cn.org.hentai.simulator.task.event.EventDispatcher;
import cn.org.hentai.simulator.task.eventloop.RunnerManager;
import cn.org.hentai.simulator.task.net.ConnectionPool;
import cn.org.hentai.simulator.util.BeanUtils;
import cn.org.hentai.simulator.util.Configs;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

/**
 * Created by matrixy on 2019/8/13.
 */
@ComponentScan(value = {"cn.org.hentai"})
@MapperScan(basePackages = "cn.org.hentai")
@SpringBootApplication
public class SimulatorApp
{
    static Logger logger = LoggerFactory.getLogger(SimulatorApp.class);

    public static void main(String[] args) throws Exception
    {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        ApplicationContext context = SpringApplication.run(SimulatorApp.class, args);
        BeanUtils.init(context);
        Configs.init(context);

        RunnerManager.init();
        EventDispatcher.init();
        ConnectionPool.init();
    }
}
