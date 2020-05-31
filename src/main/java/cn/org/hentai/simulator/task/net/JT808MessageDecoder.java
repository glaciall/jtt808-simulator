package cn.org.hentai.simulator.task.net;

import cn.org.hentai.simulator.jtt808.JTT808Message;
import cn.org.hentai.simulator.util.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by matrixy when 2020/5/10.
 */
public class JT808MessageDecoder extends ByteToMessageDecoder
{
    static Logger logger = LoggerFactory.getLogger(JT808MessageDecoder.class);

    // 缓冲区，消息体长度最大10位，再加上消息结构
    byte[] buffer = new byte[1024 + 32];

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        if (in.readableBytes() < 15) return;

        for (int i = 10; i < in.readableBytes(); i++)
        {
            if (in.getByte(in.readerIndex() + i) == 0x7E)
            {
                ByteBuf block = in.readSlice(i + 1);
                block.readBytes(buffer, 0, block.capacity());
                Packet packet = Packet.create(buffer);
                Packet query = Packet.create(packet.size());

                // 校验码验证
                packet.seek(1);
                byte crc = packet.nextByte();
                query.addByte(crc);
                for (int k = 2; k < block.capacity() - 2; k++)
                {
                    byte b = packet.nextByte();
                    query.addByte(b);
                    crc = (byte)(crc ^ b);
                }
                byte c = packet.nextByte();
                if (crc != c)
                {
                    // throw new RuntimeException(String.format("invalid crc checksum, expect: %04x, receive: %04x", crc, c));
                    logger.error(String.format("invalid crc checksum, expect: %04x, receive: %04x", crc, c));
                }

                query.seek(0);
                short id = query.nextShort();
                short attr = query.nextShort();
                short packetCount = -1, packetIndex = -1;
                int bodyLength = attr & 0x03ff;
                String sim = query.nextBCD()
                        + query.nextBCD()
                        + query.nextBCD()
                        + query.nextBCD()
                        + query.nextBCD()
                        + query.nextBCD();
                short sequence = query.nextShort();
                if ((attr & (1 << 13)) > 0)
                {
                    packetCount = query.nextShort();
                    packetIndex = query.nextShort();
                }
                byte[] body = query.nextBytes(bodyLength);
                // ByteUtils.dump(body);

                JTT808Message msg = new JTT808Message();
                msg.id = id;
                msg.sim = sim;
                msg.sequence = sequence;
                msg.body = body;
                msg.packetIndex = packetIndex;
                msg.packetCount = packetCount;
                out.add(msg);

                i = 0;
            }
        }
    }
}