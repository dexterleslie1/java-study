package com.future.demo.sleuth.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

/**
 *
 */
@SpringBootApplication
@EnableZipkinServer
public class ApplicationZipkin {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationZipkin.class, args);
    }
}
