package cn.org.hentai.simulator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by matrixy on 2019/8/18.
 */
public class BaseController
{
    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    protected final String getIP()
    {
        String addr = request.getHeader("X-Forwarded-For");
        if (null == addr) return request.getRemoteAddr();
        if (addr.indexOf(',') == -1) return addr;
        else return addr.substring(0, addr.indexOf(',')).trim();
    }

    protected void setSession(String name, Object value)
    {
        request.getSession().setAttribute(name, value);
    }

    protected <T> T getSession(String name)
    {
        return (T) request.getSession().getAttribute(name);
    }
}
