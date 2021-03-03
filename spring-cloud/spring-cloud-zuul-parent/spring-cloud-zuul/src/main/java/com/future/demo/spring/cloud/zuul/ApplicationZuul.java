package com.future.demo.spring.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
@EnableZuulProxy
public class ApplicationZuul {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationZuul.class, args);
    }

//    @Bean
//    public ZuulFilter testFilter(){
//        return new TestFilter();
//    }
}