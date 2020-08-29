package com.future.demo.txlcn;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
@EnableTransactionManagerServer
public class ApplicationTXManager {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTXManager.class, args);
    }
}