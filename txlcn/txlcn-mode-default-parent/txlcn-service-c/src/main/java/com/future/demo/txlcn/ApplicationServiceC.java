package com.future.demo.txlcn;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDistributedTransaction
public class ApplicationServiceC {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationServiceC.class, args);
    }
}