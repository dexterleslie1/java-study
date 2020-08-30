package com.future.demo.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ApplicationServiceB {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationServiceB.class, args);
    }
}