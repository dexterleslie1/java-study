package com.future.study.http.library.testing.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Dexterleslie.Chan
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.future.study.http"})
public class ApplicationBooter {
    public static void main(String []args){
        SpringApplication.run(ApplicationBooter.class, args);
    }
}
