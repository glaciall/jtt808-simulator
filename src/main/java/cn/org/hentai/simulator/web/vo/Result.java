package cn.org.hentai.simulator.web.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matrixy when 2017/8/26.
 */
public class Result
{
    private ResultError error = null;
    private Object data = null;

    public Result()
    {
        this.error = new ResultError(0, null);
    }

    public Result(int code, String message)
    {
        this.error = new ResultError(code, message);
    }

    public static Result error(int code, String reason)
    {
        return new Result(code, reason);
    }

    public static Result error(Exception ex)
    {
        return new Result(1, ex.getMessage());
    }

    public static final Map<String, Object> values(Object... args)
    {
        HashMap<String, Object> values = new HashMap<String, Object>();
        if (args.length % 2 != 0) throw new RuntimeException("参数个数必须为偶数个");
        for (int i = 0; i < args.length; i+=2)
        {
            values.put(String.valueOf(args[i]), args[i + 1]);
        }
        return values;
    }

    public ResultError getError()
    {
        return error;
    }

    public Result setError(ResultError error)
    {
        this.error = error;
        return this;
    }

    public Result setError(Exception ex)
    {
        ex.printStackTrace();
        if (ex instanceof RuntimeException)
            this.error = new ResultError(1, ex.getMessage());
        else
            this.error = new ResultError(1, "出错啦，请稍后再试");
        return this;
    }

    public Object getData()
    {
        return data;
    }

    public Result setData(Object data)
    {
        this.data = data;
        return this;
    }

    public Result withData(Object data)
    {
        return setData(data);
    }

    public static class ResultError
    {
        private int code;
        private String reason;

        public ResultError(int code, String reason)
        {
            this.code = code;
            this.reason = reason;
        }

        public int getCode()
        {
            return code;
        }

        public void setCode(int code)
        {
            this.code = code;
        }

        public String getReason()
        {
            return reason;
        }

        public void setReason(String reason)
        {
            this.reason = reason;
        }
    }
}
