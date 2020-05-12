package cn.org.hentai.simulator.task;

import cn.org.hentai.simulator.jtt808.JTT808Message;
import cn.org.hentai.simulator.task.event.EventEnum;
import cn.org.hentai.simulator.task.event.Listen;
import cn.org.hentai.simulator.task.event.EventDispatcher;
import cn.org.hentai.simulator.task.eventloop.Executable;
import cn.org.hentai.simulator.task.eventloop.RunnerManager;
import cn.org.hentai.simulator.task.net.ConnectionPool;
import cn.org.hentai.simulator.util.ByteUtils;
import cn.org.hentai.simulator.util.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by matrixy on 2020/5/9.
 */
public class SimpleDriveTask extends AbstractDriveTask
{
    static Logger logger = LoggerFactory.getLogger(SimpleDriveTask.class);

    // 部标808协议连接id
    String connectionId;

    // 1078多媒体传输协议接连id
    String multimediaConnectionId;

    // 消息包流水号
    int sequence = 1;

    // 最后发送的消息ID
    int lastSentMessageId = 0;
    Map<Integer, Integer> message;

    // 连接池
    ConnectionPool pool = ConnectionPool.getInstance();

    @Listen(name = EventEnum.message_received)
    public void onServerMessage(JTT808Message msg)
    {
        // HOWTO: 得想个办法把这个方法也触发一下，用来做交互日志就挺好的
    }

    @Listen(name = EventEnum.connected)
    public void onConnected()
    {
        logger.info("连接成功了？");

        // 连接成功时，发送鉴权消息
        JTT808Message msg = new JTT808Message();
        msg.id = 0x0102;
        msg.body = Packet.create(32)
                .addBytes("01234567890123456789012345678900".getBytes())
                .getBytes();
        send(msg);
    }

    @Listen(name = EventEnum.message_received, attachment = "8001")
    public void onGenericResponse(JTT808Message msg)
    {
        int answerSequence = ByteUtils.getShort(msg.body, 0, 2) & 0xffff;
        int answerMessageId = ByteUtils.getShort(msg.body, 2, 2) & 0xffff;
        int result = msg.body[4] & 0xff;
        logger.info(String.format("answer -> seq: %4d, id: %04x, result: %02d", answerSequence, answerMessageId, result));

        // TODO: 应该整个hashmap保存上一次发送的消息ID，KEY为流水号

        switch (lastSentMessageId)
        {
            // 鉴权消息
            case 0x0102 : onAuthenticateResponsed(result == 0x00); break;

            // 其它就不管了
        }

        lastSentMessageId = 0;
    }

    public void onAuthenticateResponsed(boolean isSuccess)
    {
        if (isSuccess)
        {
            startSession();
        }
        else
        {
            String sn = getParameter("device.id");
            byte[] vin = new byte[0];
            try { vin = "京X00001".getBytes("GBK"); } catch(Exception ex) { }

            JTT808Message msg = new JTT808Message();
            msg.id = 0x0100;
            msg.body = Packet.create(64)
                    .addShort((short)0x0001)
                    .addShort((short)0x0001)
                    .addBytes("CHINA".getBytes(), 5)
                    .addBytes("HENTAI-SIMULATOR".getBytes(), 20)
                    .addBytes(sn.getBytes(), 7)
                    .addByte((byte)0x01)
                    .addBytes(vin)
                    .getBytes();

            send(msg);
        }
    }

    @Listen(name = EventEnum.message_received, attachment = "8100")
    public void onRegisterResponsed(JTT808Message msg)
    {
        logger.info("注册有结果了？？？");
        int result = msg.body[2] & 0xff;
        if (result == 0x00)
        {
            startSession();
        }
        else
        {
            logger.error("register failed...");
        }
    }

    @Listen(name = EventEnum.disconnected)
    public void onDisconnected()
    {
        logger.info("connection lost...");
    }

    @Override
    public void startup()
    {
        EventDispatcher.register(this);
        connectionId = pool.connect("localhost", 20021, this);
    }

    // 开始正常会话，发送心跳与位置
    protected void startSession()
    {
        executeConstantly(new Executable()
        {
            @Override
            public void execute(AbstractDriveTask driveTask)
            {
                ((SimpleDriveTask)driveTask).heartbeat();
            }
        }, 1000);

        executeConstantly(new Executable()
        {
            @Override
            public void execute(AbstractDriveTask driveTask)
            {
                ((SimpleDriveTask)driveTask).reportLocation();
            }
        }, 3000);
    }

    public void reportLocation()
    {
        logger.debug("{}: report location...", getParameter("device.id"));
    }

    public void heartbeat()
    {
        logger.debug("{}: heartbeat...", getParameter("device.id"));
    }

    @Override
    public void send(JTT808Message msg)
    {
        try
        {
            msg.sim = getParameter("device.sim");
            msg.sequence = (short)((sequence++) & 0xffff);
            pool.send(connectionId, msg);

            lastSentMessageId = msg.id;

            logger.info("send: {} -> {}", msg.sim, msg.sequence);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}