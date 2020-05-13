package cn.org.hentai.simulator.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by matrixy on 2020/5/14.
 */
@Controller
public class IndexController extends BaseController
{
    @RequestMapping("/")
    public String index()
    {
        return "redirect:/route/index";
    }
}