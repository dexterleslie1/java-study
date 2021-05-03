package com.future.study.spring.amqp.direct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    @Autowired
    AmqpTemplate amqpTemplate;

    @RequestMapping("test1")
    public void test1() {
        amqpTemplate.convertAndSend(Config.ExchangeName, Config.RoutingKey, "当前服务器时间：" + new Date());
    }
}
