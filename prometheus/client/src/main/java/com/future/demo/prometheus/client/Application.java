package com.future.demo.prometheus.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication
public class Application {
    static Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(Application.class, args);
    }
}
