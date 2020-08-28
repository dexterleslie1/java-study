package com.future.demo.spring.cloud.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
@EnableEurekaClient
public class ApplicationRibbon {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRibbon.class, args);
    }

    /**
     *
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}