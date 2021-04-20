package com.future.demo.architecture.hello.world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ApplicationHelloworld {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationHelloworld.class, args);
    }
}