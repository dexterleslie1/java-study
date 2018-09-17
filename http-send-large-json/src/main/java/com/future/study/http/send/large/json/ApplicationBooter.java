package com.future.study.http.send.large.json;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dexterleslie.Chan
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.future.study.http.send"})
public class ApplicationBooter {
    public static void main(String []args){
        SpringApplication.run(ApplicationBooter.class, args);
    }
}
