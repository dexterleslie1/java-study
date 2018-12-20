package com.future.study.ansible.auto.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Dexterleslie.Chan
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.future.study.ansible"})
public class Application {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(Application.class, args);
    }
}
