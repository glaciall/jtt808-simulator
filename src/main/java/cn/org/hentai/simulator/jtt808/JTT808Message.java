package cn.org.hentai.simulator.jtt808;

/**
 * Created by houcheng when 2018/7/1.
 * TODO: 此类应该作为抽象类，需要由子类来实现2013版或2019版的定义，便于编解码的分离和协议的切换和兼容
 */
public class JTT808Message {
    static final byte[] ZERO_LENGTH_ARRAY = new byte[0];

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

    /**
     * 默认2013版
     */
    public JTT808Version version = JTT808Version.JTT808_2013;

    // 消息体
    public byte[] body;

    public JTT808Message() {
        // ...
    }

    public JTT808Message(int id) {
        this.id = id;
        this.body = ZERO_LENGTH_ARRAY;
    }

    public JTT808Message(int id, byte[] body) {
        this.id = id;
        this.body = body;
    }

    public JTT808Message(int id, String sim, byte[] body) {
        this.id = id;
        this.sim = sim;
        this.body = body;
    }

    @Override
    public String toString() {
        return "JTT808Message{" + "id=" + id + ", sim='" + sim + '\'' + ", packetIndex=" + packetIndex + ", packetCount=" + packetCount + ", sequence=" + sequence + '}';
    }
}
