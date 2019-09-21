package com.apploidxxx.heliosbackend.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Arthur Kupriyanov
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String mainPage(){
        return "index.html";
    }
    @RequestMapping("")
    public String main(){
        return "/index.html";
    }

    @RequestMapping("/policy")
    public String policyPage(){
        return "policy/index.html";
    }

    @RequestMapping("/policy/")
    public String policy(){
        return "index.html";
    }
}
