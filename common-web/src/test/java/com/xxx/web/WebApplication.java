package com.xxx.web;

import com.google.common.collect.ImmutableMap;
import common.api.json.CodeMessage;
import common.api.json.Json;
import common.exception.ServiceException;
import common.web.annotation.EnableJsonBody;
import common.web.spring.handler.JsonBodyExceptionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
@EnableJsonBody
public class WebApplication {

    @Bean
    public JsonBodyExceptionResolver jsonBodyExceptionResolver() {
        JsonBodyExceptionResolver resolver = new JsonBodyExceptionResolver();
        resolver.setExceptionMapping(ImmutableMap.of(
                SQLException.class, new ServiceException(-1, "SQL异常"))
        );
        return resolver;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}

