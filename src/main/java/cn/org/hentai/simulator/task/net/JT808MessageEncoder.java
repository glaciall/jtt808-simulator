package cn.org.hentai.simulator.task.net;

import cn.org.hentai.simulator.jtt808.JTT808Encoder;
import cn.org.hentai.simulator.jtt808.JTT808Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by matrixy on 2020/5/10.
 */
public class JT808MessageEncoder extends MessageToByteEncoder<JTT808Message>
{
    static Logger logger = LoggerFactory.getLogger(JT808MessageEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, JTT808Message msg, ByteBuf out) throws Exception
    {
        out.writeBytes(JTT808Encoder.encode(msg));
    }
}