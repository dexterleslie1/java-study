package com.future.demo.java.spring.boot.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
public class Application {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(Application.class, args);
    }
}
