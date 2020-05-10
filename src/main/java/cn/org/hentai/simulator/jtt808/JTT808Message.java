package cn.org.hentai.simulator.jtt808;

/**
 * Created by houcheng on 2018/7/1.
 */
public class JTT808Message
{
    // 消息id
    public int id;

    // 终端手机号
    public String sim;

    // 当前包序号
    public int packetIndex;

    // 总包数量
    public int packetCount;

    // 消息流水号
    public int sequence;

    // 消息体
    public byte[] body;

    public JTT808Message()
    {
        // ...
    }

    public JTT808Message(int id, String sim, byte[] body)
    {
        this.id = id;
        this.sim = sim;
        this.body = body;
    }

    @Override
    public String toString()
    {
        return "JTT808Message{" + "id=" + id + ", sim='" + sim + '\'' + ", packetIndex=" + packetIndex + ", packetCount=" + packetCount + ", sequence=" + sequence + '}';
    }
}
