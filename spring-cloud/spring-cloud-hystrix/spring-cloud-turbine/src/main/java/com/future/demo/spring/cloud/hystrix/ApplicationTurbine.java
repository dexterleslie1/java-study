package com.future.demo.spring.cloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author dexterleslie@gmail.com
 */
@EnableEurekaClient
@SpringBootApplication
@EnableTurbine
@EnableHystrixDashboard
public class ApplicationTurbine {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTurbine.class, args);
    }
}