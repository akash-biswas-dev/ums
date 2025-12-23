package com.ums.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactClientHandler {

    @GetMapping(value = {"/ums/**", "/auth"})
    public String reactApplicationHandler() {
        return "forward:/index.html";
    }
}
