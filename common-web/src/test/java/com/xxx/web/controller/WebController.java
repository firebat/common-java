package com.xxx.web.controller;

import com.google.common.collect.ImmutableMap;
import common.web.annotation.JsonBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
@RequestMapping("/api")
public class WebController {

    @RequestMapping("/rest")
    @ResponseBody
    public Object rest() {
        return ImmutableMap.of("name", "alice");
    }

    @RequestMapping("/hello")
    @JsonBody
    public Object hello(@RequestParam String name) {
        return name;
    }

    @RequestMapping("/exception")
    @JsonBody(logException = false)
    public Object exception() {
        throw new IllegalArgumentException("Oops");
    }

    @RequestMapping("/mapped")
    @JsonBody
    public Object mapped() throws Exception{
        throw  new SQLException("xxx");
    }
}

