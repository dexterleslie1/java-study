package com.future.demo.http.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
//@ComponentScan(basePackages = "com.future.study")
public class Application {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(Application.class, args);
    }
}
