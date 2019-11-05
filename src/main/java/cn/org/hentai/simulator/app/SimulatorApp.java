package cn.org.hentai.simulator.app;

import cn.org.hentai.simulator.cache.DiskCacheManager;
import cn.org.hentai.simulator.cache.DiskspaceRecycleMonitor;
import cn.org.hentai.simulator.cache.FlowStatManager;
import cn.org.hentai.simulator.server.AIOHttpServer;
import cn.org.hentai.simulator.server.cache.DBCacheManager;
import cn.org.hentai.simulator.server.cache.FileCacheManager;
import cn.org.hentai.simulator.server.socket.ConnectionPool;
import cn.org.hentai.simulator.server.transmission.TransmissionManager;
import cn.org.hentai.simulator.util.BeanUtils;
import cn.org.hentai.simulator.util.Configs;
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
@SpringBootApplication
public class SimulatorApp
{
    static Logger logger = LoggerFactory.getLogger(SimulatorApp.class);

    public static final String VERSION_CODE = "1.0.0";

    public static void main(String[] args) throws Exception
    {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        ApplicationContext context = SpringApplication.run(SimulatorApp.class, args);
        BeanUtils.init(context);
        Configs.init(context);
        CachedSettings.init();
        ConnectionPool.init();

        FlowStatManager.init();
        DiskCacheManager.init();
        // ProxyManager.init();
        ConnectionPool.init();
        FileCacheManager.init();
        DBCacheManager.init();

        logger.info("Cthulhu Server started at: {}", Configs.get("server.port"));

        // RuleManager.getInstance().init();
        // NameServer.getInstance().init();
        // RecursiveResolver.getInstance().init();

        // new HTTPServer().start();
        new DiskspaceRecycleMonitor().start();

        TransmissionManager.init();
        AIOHttpServer.init();
    }
}
