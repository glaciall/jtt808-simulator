package cn.org.hentai.simulator.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by houcheng when 2018/12/3.
 * 创建sim卡号，直接递增涨上去就是
 */
public class SIMGenerator
{
    static final AtomicLong sequence = new AtomicLong(100000000000L);

    // 返回一个新的sim卡号
    public static long get()
    {
        return sequence.addAndGet(1);
    }
}
