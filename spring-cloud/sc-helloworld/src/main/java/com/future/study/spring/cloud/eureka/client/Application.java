package com.future.study.spring.cloud.eureka.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dexterleslie@gmail.com
 */
@EnableEurekaClient
@SpringBootApplication
@RestController
public class Application {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${server.port}")
    String port;
    @RequestMapping("/user/v1/login/interface1")
    public String home() {
        return "hello world from port " + port;
    }
}