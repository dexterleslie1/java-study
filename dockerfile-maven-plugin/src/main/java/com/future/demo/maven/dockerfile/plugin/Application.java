package com.future.demo.maven.dockerfile.plugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Dexterleslie.Chan
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
