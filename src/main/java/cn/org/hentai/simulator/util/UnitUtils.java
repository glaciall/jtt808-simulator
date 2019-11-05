package cn.org.hentai.simulator.util;

import java.math.BigDecimal;

/**
 * Created by matrixy on 2019/8/25.
 */
public final class UnitUtils
{
    private static final BigDecimal SIZE_UNIT_G = new BigDecimal(1024L * 1024 * 1024);
    private static final BigDecimal SIZE_UNIT_M = new BigDecimal(1024L * 1024);
    private static final BigDecimal SIZE_UNIT_K = new BigDecimal(1024L);

    public static String getSize(long size)
    {
        BigDecimal num = new BigDecimal(size);
        if (size == 0) return "0B";
        if (size > 1024L * 1024 * 1024) return num.divide(SIZE_UNIT_G).setScale(2, BigDecimal.ROUND_DOWN) + "G";
        else if (size > 1024L * 1024) return num.divide(SIZE_UNIT_M).setScale(2, BigDecimal.ROUND_DOWN) + "M";
        else return num.divide(SIZE_UNIT_K).setScale(2, BigDecimal.ROUND_DOWN) + "K";
    }
}
