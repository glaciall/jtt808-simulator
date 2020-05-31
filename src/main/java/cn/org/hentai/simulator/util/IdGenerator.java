package cn.org.hentai.simulator.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by houcheng when 2018/12/3.
 */
public final class IdGenerator
{
    static final AtomicLong sequence = new AtomicLong(10000000000L);

    public static long next()
    {
        return sequence.addAndGet(1);
    }
}
