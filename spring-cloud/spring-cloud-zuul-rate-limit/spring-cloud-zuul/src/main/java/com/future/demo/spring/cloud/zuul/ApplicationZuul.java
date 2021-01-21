package com.future.demo.spring.cloud.zuul;

import io.github.bucket4j.grid.GridBucketState;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;

/**
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
@EnableZuulProxy
public class ApplicationZuul {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationZuul.class, args);
    }

    private Ignite ignite;

    @Bean
    @Qualifier("RateLimit")
    public IgniteCache<String, GridBucketState> cache() {
        ignite = Ignition.getOrStart(new IgniteConfiguration());
        return ignite.getOrCreateCache("rateLimit");
    }

    @PreDestroy
    public void destroy() {
        ignite.destroyCache("rateLimit");
    }
}