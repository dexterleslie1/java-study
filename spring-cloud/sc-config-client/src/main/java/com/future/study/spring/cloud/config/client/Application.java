package com.future.study.spring.cloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dexterleslie@gmail.com
 */
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

    @Value("${hello}")
    String hello;
    @RequestMapping(value = "/hello")
    public String hello(){
        return "你好，" + hello;
    }
}