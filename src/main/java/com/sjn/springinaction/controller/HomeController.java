package com.sjn.springinaction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sjn.springinaction.config.WebConfig;

/**
 * @author Sui
 * @date 2019.04.15 14:20
 */
@Controller
public class HomeController {

    /**
     * 可以使用简单方式实现
     * @see WebConfig
     * @return
     */
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
