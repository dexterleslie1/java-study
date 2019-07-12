package com.future.study.spring.boot.pom.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Dexterleslie.Chan
 */
@SpringBootApplication
//@ComponentScan(basePackages = "com.future.study")
public class ApplicationSpringbootPomConfig {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(ApplicationSpringbootPomConfig.class, args);
    }
}
