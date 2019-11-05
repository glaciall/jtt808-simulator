package cn.org.hentai.simulator.jtt808;

/**
 * Created by houcheng on 2018/7/1.
 */
public class JTTMessage
{
    // 消息id
    public short id;

    // 终端手机号
    public String sim;

    // 当前包序号
    public short packetIndex;

    // 总包数量
    public short packetCount;

    // 消息流水号
    public short sequence;

    // 消息体
    public byte[] body;

    public JTTMessage()
    {
        // ...
    }

    public JTTMessage(short id, String sim, byte[] body)
    {
        this.id = id;
        this.sim = sim;
        this.body = body;
    }

    @Override
    public String toString()
    {
        return "JTTMessage{" + "id=" + id + ", sim='" + sim + '\'' + ", packetIndex=" + packetIndex + ", packetCount=" + packetCount + ", sequence=" + sequence + '}';
    }
}
