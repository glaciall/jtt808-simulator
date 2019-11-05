package cn.org.hentai.simulator.state;

/**
 * Created by houcheng on 2018/11/12.
 * 1. handle()，在这一步里，如果是一次性的，直接返回success/failure即可，给出none结果将再次运行当前状态的handle，给出wait令其等待结果再确定
 * 2. wait or next or repeat
 * 3. next()，这一步可能返回当前步骤，所以可以构成循环，也可以在这一步通过其它信息决定其返回值，可选择性的跳转至目标状态
 */
public abstract class IState<T> implements Recyclable
{
    // 处理的结果
    public static enum Result
    {
        none("无"), wait("等待"), success("成功"), failure("失败"), finality("终结");

        private final String name;
        public String value() {
            return this.name;
        }
        public String getValue() {
            return this.name;
        }
        Result(String name) {
            this.name = name;
        }
    };

    private Result result = Result.none;

    // 当前状态的执行入口方法
    public abstract Result handle(T x);

    // 当状态进入到wait状态时，执行以下方法进行是否进入下一步的测试
    public void test(T x)
    {
        // 在这里完成对wait的唤醒尝试
    }

    // 当当前状态执行成功时跳转的目标状态
    public abstract Class<? extends IState> next();

    // 当当前状态执行出错时跳转的目标状态
    public abstract Class<? extends IState> error();

    // 获取当前状态的运行结果
    public final Result getResult()
    {
        return this.result;
    }

    // 通知当前状态的运行结果
    public synchronized final void notify(Result result)
    {
        this.result = result;
        // Log.debug(String.format("%s notified: %s", this.getClass().getSimpleName(), result.name()));
    }

    // 用于提供当前状态的描述性名称
    public abstract String name();

    @Override
    public void recycle()
    {
        this.result = Result.none;
    }
}
