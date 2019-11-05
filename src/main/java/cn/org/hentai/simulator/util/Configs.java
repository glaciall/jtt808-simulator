package cn.org.hentai.simulator.util;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.File;

/**
 * Created by matrixy on 2017/8/14.
 */
public final class Configs
{
    static Environment env;
    static String serialNumber;
    static String salt;

    public static void init(ApplicationContext context)
    {
        env = context.getEnvironment();

        // 读取序列号
        File sn = new File("serialNumber");
        if (sn.exists() == false) serialNumber = "111111111111111111111111111111";
        else serialNumber = new String(FileUtils.read(sn)).trim();

        // 读取salt
        File saltFile = new File("salt");
        if (saltFile.exists() == false) salt = "C489THHRKJIH298IOJ3KJ";
        else salt = new String(FileUtils.read(saltFile)).trim();
    }

    public static String getSerialNumber()
    {
        return serialNumber;
    }

    public static String getPasswordSalt()
    {
        return salt;
    }

    public static String get(String key)
    {
        Object val = env.getProperty(key);
        if (null == val) return null;
        else return String.valueOf(val).trim();
    }

    public static String get(String key, String defaultVal)
    {
        return env.getProperty(key, defaultVal);
    }

    public static int getInt(String key, int defaultVal)
    {
        String val = get(key, String.valueOf(defaultVal));
        return Integer.parseInt(val);
    }
}
