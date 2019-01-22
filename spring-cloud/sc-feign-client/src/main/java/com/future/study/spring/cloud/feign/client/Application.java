package com.future.study.spring.cloud.feign.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients
public class Application {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    HelloService helloService;
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String sayHello(){
        String hello = helloService.sayHello();
        return hello;
    }
}