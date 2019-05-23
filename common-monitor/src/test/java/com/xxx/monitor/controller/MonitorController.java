package com.xxx.monitor.controller;

import common.monitor.annotation.Timed;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MonitorController {

    @GetMapping("/echo")
    @ResponseBody
    @Timed("echo")
    public Object echo(@RequestParam String data) {
        return data;
    }

    @GetMapping("/hello")
    @ResponseBody
    @Timed
    public Object hello(@RequestParam String name) {
        return "Hello " + name;
    }
}
