package com.example.RoadSideApiGenericWay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/")
    public String get(){
        return "index";
    }
}