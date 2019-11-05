package cn.org.hentai.simulator.jtt808;

import cn.org.hentai.simulator.util.ByteHolder;
import cn.org.hentai.simulator.util.Packet;

/**
 * Created by houcheng on 2018/7/1.
 */
public class JTT808Decoder
{
    ByteHolder buffer = new ByteHolder(4096 * 100);

    public void write(byte[] block)
    {
        buffer.write(block);
    }

    public void write(byte[] block, int startIndex, int length)
    {
        for (int i = 0; i < length; i++) buffer.write(block[i + startIndex]);
    }

    public Packet read()
    {
        if (this.buffer.size() < 13) return null;
        if (this.buffer.get(0) != 0x7e) throw new RuntimeException("wrong jtt808 packet");

        for (int i = 1, l = this.buffer.size(); i < l; i++)
        {
            if (this.buffer.get(i) == 0x7e)
            {
                Packet packet = Packet.create(i + 1);
                byte last = this.buffer.get(0);
                for (int k = 0; k <= i; k++)
                {
                    byte b = this.buffer.get(k);
                    if (b == 0x7d)
                    {
                        k++;
                        byte n = this.buffer.get(k);
                        if (n == 0x01) b = 0x7d;
                        else if (n == 0x02) b = 0x7e;
                        else throw new RuntimeException(String.format("error quote after 0x7d: 0x%x", n));
                    }
                    packet.addByte(b);
                }
                this.buffer.slice(i + 1);
                return packet;
            }
        }

        return null;
    }
}
