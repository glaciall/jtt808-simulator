package cn.org.hentai.simulator.entity;

import cn.org.hentai.simulator.jtt808.JTT808Decoder;
import cn.org.hentai.simulator.jtt808.JTT808Encoder;
import cn.org.hentai.simulator.jtt808.JTT808Message;
import cn.org.hentai.simulator.manager.ThreadManager;
import cn.org.hentai.simulator.state.IState;
import cn.org.hentai.simulator.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;

/**
 * @ClassName: com.transnova.generator.simulator.model.XVehicleDevice
 * @Description: 车机
 * @author:
 * @date: 2018/11/21 9:51
 * @version: V1.0
 */
public class XVehicleDevice
{

    private static Logger logger = LoggerFactory.getLogger(XVehicleDevice.class);

    private String deviceSn;
    private String sim;
    private short sequence = 0;
    private XDriver driver;

    private Socket client;
    private BufferedInputStream reader;
    private BufferedOutputStream writer;

    private JTT808Decoder decoder = new JTT808Decoder();

    public XVehicleDevice(String deviceSn, String sim)
    {
        this.deviceSn = deviceSn;
        this.sim = sim;
    }

    public void setDriver(XDriver driver)
    {
        this.driver = driver;
    }

    /**
     * 连接
     */
    public void connect()
    {
        try
        {
            this.client = new Socket();
            this.client.setSoTimeout(5000);
            this.client.setSendBufferSize(8192);
            this.client.setReceiveBufferSize(8192);
            this.client.connect(new InetSocketAddress(Configs.get("vehicle-server.addr"), Integer.parseInt(Configs.get("vehicle-server.port"))), 200);
            this.reader = new BufferedInputStream(this.client.getInputStream(), 4096);
            this.writer = new BufferedOutputStream(this.client.getOutputStream(), 4096 * 100);
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public void receive(Task task)
    {
        try
        {
            int buffSize = this.reader.available();
            if (buffSize <= 0) return;

            byte[] block = new byte[buffSize];
            int len = 0;
            if ((len = this.reader.read(block)) != buffSize) logger.debug(String.format("读取失败了，预期：%d，实际：%d", buffSize, len));
            // decoder.write(block, 0, len);

            while (true)
            {
                Packet packet = null; // decoder.read();
                if (null == packet)
                {
                    break;
                }

                Packet query = Packet.create(packet.size());

                // 校验码验证
                packet.seek(1);
                byte crc = packet.nextByte();
                query.addByte(crc);
                for (int i = 2; i < packet.size() - 2; i++)
                {
                    byte b = packet.nextByte();
                    query.addByte(b);
                    crc = (byte) (crc ^ b);
                }
                byte c = packet.nextByte();
                if (crc != c)
                {
                    throw new RuntimeException(String.format("invalid crc checksum, expect: %x, receive: %x", crc, c));
                }

                query.seek(0);
                short id = query.nextShort();
                short attr = query.nextShort();
                short packetCount = -1, packetIndex = -1;
                int bodyLength = attr & 0x03ff;
                String sim = query.nextBCD() + query.nextBCD() + query.nextBCD() + query.nextBCD() + query.nextBCD() + query.nextBCD();
                short sequence = query.nextShort();
                if ((attr & (1 << 12)) > 0)
                {
                    packetCount = query.nextShort();
                    packetIndex = query.nextShort();
                }
                byte[] body = query.nextBytes(bodyLength);

                JTT808Message msg = new JTT808Message();
                msg.id = id;
                msg.sim = sim;
                msg.sequence = sequence;
                msg.body = body;
                msg.packetIndex = packetIndex;
                msg.packetCount = packetCount;

                // Log.debug(String.format("Receive: %x : %d, %s", msg.id, msg.sequence, ByteUtils.toString(msg.body)));

                // 驾驶员认证应答
                if (msg.id == (short) 0x8f01)
                {
                    if (msg.body[0] == 0x00)
                    {
                        IState state = task.getState();
                        if (!"驾驶员认证".equals(state.name())) throw new RuntimeException("我可去你的吧。。。");
                        state.notify(IState.Result.success);
                        task.getDriver().setId(ByteUtils.getLong(msg.body, 1, 8));
                        task.getDriver().setName(new String(msg.body, 10, msg.body[9], "GBK"));
                        logger.info(String.format("驾驶员：%s，认证成功...", new String(msg.body, 10, msg.body[9], "GBK")));
                    }
                    else
                    {
                        task.getState().notify(IState.Result.failure);
                        logger.error("驾驶员身份认证失败...");
                    }
                }

                // 鉴权应答
                if (msg.id == (short) 0x8001 && msg.body[2] == 0x01 && msg.body[3] == 0x02)
                {
                    logger.error(String.format("鉴权应答：%x", msg.body[4]));
                    task.getState().notify(msg.body[4] == 0x00 ? IState.Result.success : IState.Result.failure);
                }

                // 实时视频调阅
                if (msg.id == (short) 0x8f07)
                {
                    int channel = msg.body[0] & 0xff;
                    int seconds = msg.body[1] & 0xff;
                    String rtmp = new String(msg.body, 2, msg.body.length - 2);
                    logger.info("request-streaming-video, channel: {}, seconds: {}, rtmp addr: {}", channel, seconds, rtmp);
                    pushVideoStream(channel, seconds, rtmp);
                }
            }
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    Process pusher1, pusher2;
    private void pushVideoStream(int channel, int seconds, String rtmp)
    {
        Process pusher = channel == 1 ? pusher1 : pusher2;
        try
        {
            if (pusher == null || pusher.isAlive() == false)
            {
                String videoFile = getVideo(channel);
                String ffmpeg = Configs.get("ffmpeg.path");
                pusher = Runtime.getRuntime().exec(String.format("%s -re -i %s -c copy -f flv %s", ffmpeg, videoFile, rtmp));

                if (channel == 1) pusher1 = pusher;
                if (channel == 2) pusher2 = pusher;
            }
        }
        catch(Exception ex)
        {
            logger.error("push stream video error", ex);
        }
    }

    private String getVideo(int channel)
    {
        File dir = new File(Configs.get("video.path") + File.separator + (channel == 1 ? "adas" : "dms") + File.separator);
        File[] videos = dir.listFiles();
        return videos[(int)(Math.random() * videos.length)].getAbsolutePath();
    }

    /**
     * 车机鉴权
     */
    long startTime = 0L;
    private void showTimeCost()
    {
        logger.info("task: {}, authenticate spend: {}", this.getDeviceSn(), System.currentTimeMillis() - startTime);
    }

    public void register()
    {
        Packet packet = Packet.create(64);
        packet.addShort((short)0x0000);
        packet.addShort((short)0x0000);
        packet.addBytes("XX-OO".getBytes());
        packet.addBytes("01234567890123456789".getBytes());
        packet.addBytes(deviceSn.getBytes());
        packet.addByte((byte)0x00);
        try { packet.addBytes("京12345".getBytes("GBK")); } catch(Exception e) { }

        startTime = System.currentTimeMillis();
        JTT808Message msg = new JTT808Message();
        msg.sim = this.sim;
        msg.id = 0x0100;
        msg.sequence = next();
        msg.body = packet.getBytes();

        // 发送消息到车机平台
        this.send(msg);
        this.commitPendings();
    }

    public void authenticate()
    {
        startTime = System.currentTimeMillis();
        JTT808Message msg = new JTT808Message();
        msg.sim = this.sim;
        msg.id = 0x0102;
        msg.sequence = next();
        msg.body = getAuthenticateCode().getBytes();

        // 发送消息到车机平台
        this.send(msg);
        this.commitPendings();
    }

    private String getAuthenticateCode()
    {
        return this.deviceSn + ":" + MD5.encode(this.sim + ":::JCI90Y43P2H3FAGYVG34QPYYO32");
    }

    /**
     * 驾驶员认证
     */
    public void driverAuthenticate()
    {
        int len;
        byte[] block = new byte[1000];

        // 获取照片路径
        String photoPath = this.takePhoto(this.driver);
        if (StringUtils.isEmpty(photoPath)) {
            return ;
        }
        try (FileInputStream fis = new FileInputStream(photoPath))
        {
            int i = 1;
            int blocks = (int) Math.ceil(new File(photoPath).length() / 1000f);
            while (true)
            {
                len = fis.read(block);
                if (len <= 0)
                {
                    break;
                }

                byte[] data;
                if (i == 1)
                {
                    data = new byte[len + 9];
                    System.arraycopy(block, 0, data, 9, len);
                    System.arraycopy(ByteUtils.toBytes((int) (114.12345678f * 1000000)), 0, data, 0, 4);
                    System.arraycopy(ByteUtils.toBytes((int) (41.87654321f * 1000000)), 0, data, 4, 4);
                    data[8] = 1;
                }
                else
                {
                    data = new byte[len];
                    System.arraycopy(block, 0, data, 0, len);
                }
                JTT808Message msg = new JTT808Message();
                msg.id = 0x0f01;
                msg.sequence = next();
                msg.packetIndex = (short) i;
                msg.packetCount = (short) blocks;
                msg.sim = this.sim;
                msg.body = data;
                i += 1;
                // 发送消息
                this.send(msg);
            }
            this.commitPendings();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 拍照
     */
    public String takePhoto(XDriver driver)
    {
        return driver.getPhotoPath();
    }

    /**
     * 发送消息，注意，要跟commitPendings()一起使用
     */
    private void send(JTT808Message msg)
    {
        // this.connect.send(JTT808Encoder.encode(msg));
        // TODO: 将要发送的内容放到缓冲区里，然后由其它的线程池去专门完成发送的事情
        synchronized (pendingMessages)
        {
            pendingMessages.addLast(msg);
        }
    }

    // send(JTT808Message msg)要跟此方法一起使用
    private void commitPendings()
    {
        ThreadManager.depute(this);
    }

    private boolean send()
    {
        if (pendingMessages.size() == 0) return false;
        JTT808Message msg = null;
        synchronized (pendingMessages)
        {
            msg = pendingMessages.removeFirst();
        }
        try
        {
            this.writer.write(JTT808Encoder.encode(msg));
            return true;
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    // 此方法是交给其它线程来执行的
    public void sendAllPendings()
    {
        int count = 0;
        while (send()) count++;
        try { this.writer.flush(); } catch(Exception e) { }
    }

    LinkedList<JTT808Message> pendingMessages = new LinkedList<>();

    /**
     * 发送位置信息
     */
    public Point sendTrack(Point position) throws Exception
    {
        float speed = position.getSpeed() == null ? 0 : position.getSpeed();

        byte[] timeBCD = new byte[6];

        String time = DateUtils.format(new Date(position.getReportTime()), "YYMMddHHmmss");
        for (int s = 0, k = 0; s < time.length(); s += 2)
        {
            byte ch1 = (byte) (time.charAt(s) - '0');
            byte ch2 = (byte) (time.charAt(s + 1) - '0');
            timeBCD[k++] = (byte) (ch1 << 4 | ch2);
        }

        String eventCode = position.getEvent();

        // 发送位置信息
        JTT808Message msg = new JTT808Message();
        msg.id = 0x0200;
        msg.sequence = next();
        msg.sim = this.sim;
        msg.body = Packet.create(1024).addInt(0x00).addInt(0x00).addInt((int) (position.getLatitude() * 1000000)).addInt((int) (position.getLongitude() * 1000000)).addShort((short) 0x00).addShort((short) (speed * 10)).addShort((short) 0x00).addBytes(timeBCD).getBytes();

        logger.debug(String.format("Time: %s, Location: %.6f x %.6f, Event: %s", DateUtils.format(new Date(position.getReportTime()), "HH:mm:ss"), position.getLongitude(), position.getLatitude(), eventCode));

        // 发送消息
        this.send(msg);
        this.commitPendings();
        return position;
    }

    /**
     * 发送事件信息
     */
    public void sendEvent(XEvent event) throws Exception
    {
        // 事件的开始
        String code = event.getCode();
        if (event.getType() == 1)
        {
            short msgId = 0x00;
            Packet body = Packet.create(1024);

            byte[] timeBCD = timeToBCD(event.getReportTime());

            if (code.startsWith("11") || code.startsWith("12"))
            {
                msgId = 0x0f02;
                body.addInt(event.getSequence()).addInt(Integer.parseInt(code, 16)).addInt((int) (event.getLatitude() * 1000000)).addInt((int) (event.getLongitude() * 1000000)).addShort((short) 0).addShort((short) (event.getSpeed() * 10)).addShort((short) 0).addBytes(timeBCD).addByte((byte) 0);
            }
            //
            else if (code.startsWith("22") || code.startsWith("23") || code.startsWith("24"))
            {
                msgId = 0x0f03;
                body.addInt(event.getSequence()).addInt(Integer.parseInt(code, 16)).addInt((int) (event.getLatitude() * 1000000)).addInt((int) (event.getLongitude() * 1000000)).addShort((short) 0).addShort((short) (event.getSpeed() * 10)).addShort((short) 0).addBytes(timeBCD).addByte((byte) 0);
            }
            // 车辆故障类
            else if (code.startsWith("25"))
            {
                msgId = 0x0f04;
                body.addInt(event.getSequence()).addInt(Integer.parseInt(code, 16)).addInt((int) (event.getLatitude() * 1000000)).addInt((int) (event.getLongitude() * 1000000)).addShort((short) 0).addShort((short) (event.getSpeed() * 10)).addShort((short) 0).addBytes(timeBCD);
            }
            else
            {
                throw new RuntimeException(String.format("invalid event code: %s", code));
            }
            JTT808Message msg = new JTT808Message();
            msg.sim = this.sim;
            msg.id = msgId;
            msg.sequence = next();
            msg.body = body.getBytes();

            // 发送消息
            this.send(msg);
            logger.debug(String.format("Event start: %s - %d", code, event.getSequence()));

            // 随机发送事件附件
            this.sendEventAttachment(event.getSequence(), this.getRandomFile(code, "camera", 1), 1, (byte) 0x00, (byte) 0x00);
            this.sendEventAttachment(event.getSequence(), this.getRandomFile(code, "camera", 2), 2, (byte) 0x00, (byte) 0x00);

            // 随机发送ADAS、DMS分析模型附件
            // TODO: 视情况发送消息附件，非危险驾驶的没有以下两类
            // this.sendEventAttachment(seq, this.getRandomFile(null, "aimodel-adas"), (byte) 0x03, (byte) 0x00);
            // logger.debug(String.format("Event Attachment: %s - ADAS", code));

            // 随机发送dms图片
            // this.sendEventAttachment(seq, this.getRandomFile(null, "aimodel-dms"), (byte) 0x04, (byte) 0x00);
            // logger.debug(String.format("Event Attachment: %s - DMS", code));

            // 随机发个小视频
            // 通道1
            this.sendEventAttachment(event.getSequence(), getRandomVideo(code, 1), 1, (byte) 0x02, (byte) 0x05);
            // 通道2
            this.sendEventAttachment(event.getSequence(), getRandomVideo(code, 2), 2, (byte) 0x02, (byte) 0x05);
        }
        // 事件的结束
        else
        {
            logger.debug(String.format("Event end: %s - %d", code, event.getSequence()));
            JTT808Message msg = new JTT808Message();
            msg.id = 0x0fff;
            msg.sim = this.sim;
            msg.sequence = next();
            msg.body = Packet.create(18)
                    .addInt(event.getSequence())
                    .addInt(Integer.parseInt(code, 16))
                    .addBytes(timeToBCD(event.getReportTime()))
                    .addShort((short)event.getTime1())
                    .addShort((short)event.getTime2())
                    .getBytes();
            this.send(msg);
        }

        this.commitPendings();
    }

    private byte[] timeToBCD(long timestamp)
    {
        byte[] timeBCD = new byte[6];

        String time = DateUtils.format(new Date(timestamp), "YYMMddHHmmss");
        for (int s = 0, k = 0; s < time.length(); s += 2)
        {
            byte ch1 = (byte) (time.charAt(s) - '0');
            byte ch2 = (byte) (time.charAt(s + 1) - '0');
            timeBCD[k++] = (byte) (ch1 << 4 | ch2);
        }
        return timeBCD;
    }

    JTT808Message finalityEventMessage = null;


    /**
     * 检查此目录是否存在，如果存在则继续取，如果不存在随机取
     * @param fileUrl
     * @return
     */
    private boolean checkFile(String fileUrl) {
        URL url = XVehicleDevice.class.getResource("/" + fileUrl + "/");
        return url != null;
    }


    /**
     * 随机获取一张图片
     * @param code 事件编码
     * @return
     */
    private File getRandomFile(final String code, String type, int channel)
    {
        File file = new File(Task.class.getResource("/" + type + "/").getFile());

        File[] files = null;
        if ("camera".equals(type)) {

            files = file.listFiles(new FileFilter()
            {
                @Override
                public boolean accept(File pathname)
                {
                    return pathname.getName().startsWith(code + "-" + channel + "-");
                }
            });
        } else {
            files = file.listFiles();
        }

        if (files == null || files.length == 0)
        {
            return null;
        }
        // 随机获取一张图片
        return files[(int) (Math.random() * files.length)];
    }



    /**
     * 随机获取一条视频
     * @param code
     * @return
     */
    private File getRandomVideo(final String code, int channel)
    {
        File[] files = new File(Task.class.getResource("/video/").getFile()).listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return pathname.getName().startsWith(code + "-" + channel + "-");
            }
        });
        if (files == null || files.length == 0)
        {
            return null;
        }
        // 随机获取一条视频
        return files[(int) (Math.random() * files.length)];
    }

    /**
     * 发送事件附件信息：图片等
     */
    private void sendEventAttachment(int messageId, File file, int channel, byte mmType, byte encType) throws Exception
    {
        if (null == file) return;

        int len;
        byte[] block = new byte[900];

        try (FileInputStream fis = new FileInputStream(file))
        {
            int i = 1;
            int blocks = (int) Math.ceil(file.length() / 900f);
            while (true)
            {
                len = fis.read(block);
                if (len <= 0)
                {
                    break;
                }
                byte[] data = null;
                if (i == 1)
                {
                    data = new byte[len + 36];
                    System.arraycopy(ByteUtils.toBytes(messageId), 0, data, 0, 4);
                    data[4] = mmType;
                    data[5] = encType;
                    data[6] = 0x00;
                    data[7] = (byte)(channel & 0xff);
                    // 因为车机服务器端不处理这一段信息，所以这里先写死内容了
                    System.arraycopy(ByteUtils.parse("00 00 00 00 00 00 00 00 02 73 7E 80 06 D8 E0 80 00 C8 04 C4 01 68 18 08 14 18 31 11"), 0, data, 8, 28);
                    System.arraycopy(block, 0, data, 36, len);
                }
                else
                {
                    data = new byte[len];
                    System.arraycopy(block, 0, data, 0, len);
                }

                JTT808Message msg = new JTT808Message();
                msg.id = 0x0f05;
                msg.sequence = next();
                msg.packetIndex = (short) i;
                msg.packetCount = (short) blocks;
                msg.sim = this.sim;
                msg.body = data;
                i += 1;

                // 发送消息
                // 注意，这里就不再调用commitPendings了，因为上一级已经调用过了，不过写上也没有关系
                this.send(msg);
            }
        }
    }

    /**
     * 计算索引
     *
     * @return
     */
    private short next()
    {
        return (short) ((sequence++) & 0xffff);
    }

    public String getDeviceSn()
    {
        return deviceSn;
    }

    public XVehicleDevice setDeviceSn(String deviceSn)
    {
        this.deviceSn = deviceSn;
        return this;
    }

    public String getSim()
    {
        return sim;
    }

    public XVehicleDevice setSim(String sim)
    {
        this.sim = sim;
        return this;
    }

    public XDriver getDriver()
    {
        return driver;
    }

    public void release()
    {
        try { this.reader.close(); } catch(Exception e) { }
        try { this.writer.close(); } catch(Exception e) { }
        try { this.client.close(); } catch(Exception e) { }
    }
}
