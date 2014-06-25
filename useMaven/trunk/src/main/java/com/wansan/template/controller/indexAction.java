package com.wansan.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 14-4-26.
 */

@Controller
public class indexAction {
    @RequestMapping(value = "/index")
    public String index(){
        return "redirect:/mainpage/index.html";
    }
}
