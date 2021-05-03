package com.future.study.spring.boot.logback;

import com.future.study.spring.boot.package1.TestAdditivity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void init1() {
        TestAdditivity additivity = new TestAdditivity();
        additivity.method();
    }
}
