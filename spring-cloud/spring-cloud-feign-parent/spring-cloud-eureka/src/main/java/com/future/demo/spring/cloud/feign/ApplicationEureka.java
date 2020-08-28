package com.future.demo.spring.cloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author dexterleslie@gmail.com
 */
@EnableEurekaServer
@SpringBootApplication
public class ApplicationEureka {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEureka.class, args);
    }
}