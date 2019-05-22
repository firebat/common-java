package com.xxx.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebControllerTest {

    @LocalServerPort
    private int port;

    @Resource
    private TestRestTemplate testRestTemplate;

    @Test
    public void testHello() {
        String data = this.testRestTemplate.getForObject("http://localhost:" + port + "/api/hello?name=alice", String.class);
        assertEquals("{\"code\":0,\"message\":null,\"data\":\"alice\"}", data);
    }
}
