package cn.org.hentai.simulator.task.net;

import cn.org.hentai.simulator.jtt808.JTT808Decoder;
import cn.org.hentai.simulator.jtt808.JTT808Message;
import cn.org.hentai.simulator.task.AbstractDriveTask;
import cn.org.hentai.simulator.task.event.EventCallable;
import cn.org.hentai.simulator.task.event.EventDispatcher;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by matrixy on 2020/4/30.
 */
public class ConnectionPool
{
    static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

    EventLoopGroup group = null;
    Bootstrap bootstrap = null;
    ConcurrentHashMap<String, Connection> connections = null;

    private ConnectionPool()
    {
        connections = new ConcurrentHashMap<>(1024);
        start();
    }

    private void start()
    {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>()
            {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception
                {
                    ch.pipeline()
                            .addLast(new JT808MessageDecoder())
                            .addLast(new JT808MessageEncoder())
                            .addLast(new SimpleNettyHandler());
                }
            });
    }

    public String connect(String address, int port, AbstractDriveTask watcher)
    {
        Channel chl = bootstrap.connect(address, port).channel();
        connections.put(chl.id().asLongText(), new Connection(chl, watcher));
        return chl.id().asLongText();
    }

    public void send(String channelId, Object data) throws Exception
    {
        Connection conn = connections.get(channelId);
        if (conn != null) conn.channel.writeAndFlush(data);
        else throw new SocketException("connection closed");
    }

    protected void notify(String tag, String channelId, String messageId, Object data)
    {
        for (int i = 0; i < 100 && connections.containsKey(channelId) == false; i++) try { Thread.sleep(0, 1000); } catch(Exception e) { };
        Connection conn = connections.get(channelId);
        if (conn != null)
        {
            EventDispatcher.getInstance().dispatch(conn.watcher, tag, messageId, data);
        }
        else
        {
            logger.error("no channel found for: " + channelId);
        }
    }

    public void shutdown() throws Exception
    {
        group.shutdownGracefully().sync();
    }

    static final ConnectionPool instance = new ConnectionPool();
    public static void init()
    {
        // do nothing here..
    }

    public static ConnectionPool getInstance()
    {
        return instance;
    }

    static class Connection
    {
        public Channel channel;
        public AbstractDriveTask watcher;

        public Connection(Channel channel, AbstractDriveTask watcher)
        {
            this.channel = channel;
            this.watcher = watcher;
        }
    }

    static class SimpleNettyHandler extends SimpleChannelInboundHandler<JTT808Message>
    {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception
        {
            instance.notify("connected", ctx.channel().id().asLongText(), null, null);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception
        {
            super.channelInactive(ctx);
            // System.out.println("closed..." + ctx.channel().id().asLongText());
            instance.notify("disconnected", ctx.channel().id().asLongText(), null, null);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, JTT808Message msg) throws Exception
        {
            String msgId = String.format("%04x", msg.id & 0xffff);
            // logger.debug("received: {}", msgId);
            instance.notify("message_received", ctx.channel().id().asLongText(), msgId, msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
        {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
