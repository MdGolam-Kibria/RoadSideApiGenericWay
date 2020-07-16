package com.example.RoadSideApiGenericWay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/")
    @ResponseBody
    public String afterLogin(){
        return "loginSuccess";
    }
}
