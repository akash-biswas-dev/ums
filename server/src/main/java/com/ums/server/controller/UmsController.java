package com.ums.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UmsController {

    @GetMapping(value = "/ums/**")
    public String reactApplicationHandler(){
        return "forward:/index.html";
    }
}
