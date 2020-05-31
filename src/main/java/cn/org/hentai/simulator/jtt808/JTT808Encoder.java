package cn.org.hentai.simulator.jtt808;

import cn.org.hentai.simulator.util.Packet;

import java.io.OutputStream;

/**
 * Created by matrixy when 2018-08-12.
 */
public final class JTT808Encoder
{
    public static void write(JTT808Message message, OutputStream writer)
    {
        try
        {
            writer.write(encode(message));
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static byte[] encode(JTT808Message message)
    {
        if (message.body.length > 1024) throw new RuntimeException("message body size exceed 1024: " + message.body.length);
        byte crc = (byte)(((message.id >> 8) & 0xff) ^ (message.id & 0xff));

        Packet packet = Packet.create(2000);
        short attr = (short)(message.body.length & 0x3ff);
        if (message.packetCount > 0) attr = (short)(attr | (1 << 13));
        crc = (byte)(crc ^ ((attr >> 8) & 0xff) ^ (attr & 0xff));

        byte[] sim = new byte[6];
        for (int i = 0, k = 0; i < message.sim.length(); i+=2)
        {
            char a = (char)(message.sim.charAt(i) - '0');
            char b = (char)(message.sim.charAt(i + 1) - '0');
            sim[k] = (byte)(a << 4 | b);
            crc = (byte)(crc ^ sim[k]);
            k += 1;
        }
        crc = (byte)(crc ^ ((message.sequence >> 8) & 0xff));
        crc = (byte)(crc ^ ((message.sequence) & 0xff));

        if (message.packetCount > 0)
        {
            crc = (byte)(crc ^ ((message.packetCount >> 8) & 0xff) ^ (message.packetCount & 0xff));
            crc = (byte)(crc ^ ((message.packetIndex >> 8) & 0xff) ^ (message.packetIndex & 0xff));
        }

        for (int i = 0; i < message.body.length; i++)
        {
            crc = (byte)(crc ^ message.body[i]);
        }

        // 拼包
        packet.addByte((byte)0x7e);
        packet.addByteAutoQuote((byte)(message.id >> 8));
        packet.addByteAutoQuote((byte)(message.id & 0xff));
        packet.addByteAutoQuote((byte)(attr >> 8));
        packet.addByteAutoQuote((byte)(attr & 0xff));
        for (int i = 0; i < sim.length; i++) packet.addByteAutoQuote(sim[i]);
        packet.addByteAutoQuote((byte)(message.sequence >> 8));
        packet.addByteAutoQuote((byte)(message.sequence & 0xff));

        if (message.packetCount > 0)
        {
            packet.addByteAutoQuote((byte) (message.packetCount >> 8));
            packet.addByteAutoQuote((byte) (message.packetCount & 0xff));

            packet.addByteAutoQuote((byte) (message.packetIndex >> 8));
            packet.addByteAutoQuote((byte) (message.packetIndex & 0xff));
        }

        for (int i = 0; i < message.body.length; i++) packet.addByteAutoQuote(message.body[i]);
        packet.addByteAutoQuote(crc);
        packet.addByte((byte)0x7e);

        return packet.getBytes();
    }
}
