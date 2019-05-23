package com.xxx.jdbc.controller;

import common.jdbc.annotation.ShardKey;
import common.jdbc.shard.ShardKeyStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShardController {

    @GetMapping("/master")
    @ShardKey("master")
    @ResponseBody
    public Object master() {
        return ShardKeyStore.get();
    }

    @GetMapping("/slave")
    @ResponseBody
    public Object slave() {
        return ShardKeyStore.get();
    }
}
