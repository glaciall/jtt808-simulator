package cn.org.hentai.simulator.state;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by houcheng on 2018/11/14.
 */
public abstract class StateRunner
{
    private static Logger logger = LoggerFactory.getLogger(StateRunner.class);

    private IState state;
    private long lastActiveTime;

    public final IState getState()
    {
        return state;
    }

    public final void setState(IState state)
    {
        this.state = state;
    }

    public long getLastActiveTime()
    {
        return lastActiveTime;
    }

    public StateRunner setLastActiveTime(long lastActiveTime)
    {
        this.lastActiveTime = lastActiveTime;
        return this;
    }

    /**
     * 执行当前状态的处理方法
     * @return true表示继续运行，false表示应该停止当前Runner的运行（比如从队列中删除掉）
     */
    public final boolean run()
    {
        try
        {
            IState.Result result = state.getResult();
            state.test(this);
            if (result == IState.Result.none)
            {
                result = state.handle(this);
                state.notify(result);
                this.setLastActiveTime(System.currentTimeMillis());
            }
            else if (result == IState.Result.success)
            {
                state = create(state.next());
                if (state == null) return false;
                this.setState(state);
                this.setLastActiveTime(System.currentTimeMillis());
            }
            else if (result == IState.Result.failure)
            {
                state = create(state.error());
                if (state == null) return false;
                this.setState(state);
                this.setLastActiveTime(System.currentTimeMillis());
            }
            else if (result == IState.Result.finality)
            {
                return false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            if (state != null) state.notify(IState.Result.failure);
        }
        return true;
    }

    // 本实例内缓存，不能做全局缓存，不然可能会出问题
    private IState create(Class<? extends IState> stateClass)
    {
        if (null == stateClass) return null;
        try
        {
            // IState state = stateCache.get(stateClass);
            IState state = null;
            if (state == null)
            {
                state = (IState)stateClass.newInstance();
                // stateCache.put(stateClass, state);
            }
            state.recycle();
            return state;
        }
        catch(Exception e)
        {
            logger.error("", e);
            return null;
        }
    }

    // private Map<Class, IState> stateCache = new HashMap<Class, IState>();
}
