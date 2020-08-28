package com.future.demo.spring.cloud.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author dexterleslie@gmail.com
 */
@EnableEurekaClient
@SpringBootApplication
public class ApplicationHelloworld {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationHelloworld.class, args);
    }
}