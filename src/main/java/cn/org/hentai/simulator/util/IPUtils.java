package cn.org.hentai.simulator.util;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by matrixy when 2019/4/28.
 */
public final class IPUtils
{
    public static long toInteger(String addr)
    {
        String[] parts = addr.split("\\.");
        long ip = 0;
        for (int i = 0; i < 4; i++) ip |= (Integer.parseInt(parts[i]) & 0xff) << ((3 - i) * 8);
        return ip & 0xffffffffL;
    }

    public static String fromInteger(long ip)
    {
        long a = (ip >> 24) & 0xff,
                b = (ip >> 16) & 0xff,
                c = (ip >> 8) & 0xff,
                d = ip & 0xff;
        return a + "." + b + "." + c + "." + d;
    }

    // 查找与给定的neighborAddress处于同一个局域网的本机IP地址
    public static InetAddress getLocalAddress(InetAddress neighborAddress) throws Exception
    {
        byte[] addrArray = neighborAddress.getAddress();

        boolean found = true;
        Enumeration<NetworkInterface> netInterfaces = null;
        try
        {
            netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
        }
        catch(Exception e) { return null; }
        while (netInterfaces.hasMoreElements())
        {
            NetworkInterface ni = netInterfaces.nextElement();
            String name = ni.getDisplayName();
            List<InterfaceAddress> iaList = ni.getInterfaceAddresses();

            for (InterfaceAddress ia : iaList)
            {
                byte[] ip = ia.getAddress().getAddress();
                found = true;
                for (int i = 0, k = ia.getNetworkPrefixLength() / 8; i < k && i < addrArray.length; i++)
                {
                    if (ip[i] != addrArray[i])
                    {
                        found = false;
                        break;
                    }
                }
                if (!found) continue;
                return ia.getAddress();
            }
            if (found) break;
        }

        return null;
    }
}
