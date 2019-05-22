package com.xxx.web.controller;

import common.web.annotation.JsonBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @RequestMapping("/api/hello")
    @JsonBody
    public Object hello(@RequestParam String name) {
        return name;
    }
}

