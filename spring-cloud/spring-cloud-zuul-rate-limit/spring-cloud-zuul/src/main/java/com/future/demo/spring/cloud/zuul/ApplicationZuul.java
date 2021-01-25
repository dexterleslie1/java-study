package com.future.demo.spring.cloud.zuul;

import io.github.bucket4j.grid.GridBucketState;
import org.infinispan.AdvancedCache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.functional.FunctionalMap;
import org.infinispan.functional.impl.FunctionalMapImpl;
import org.infinispan.functional.impl.ReadWriteMapImpl;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

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

    @Bean
    @Qualifier("RateLimit")
    public FunctionalMap.ReadWriteMap<String, GridBucketState> map() {
        DefaultCacheManager cacheManager = new DefaultCacheManager();
        cacheManager.defineConfiguration("rateLimit", new ConfigurationBuilder().build());
        AdvancedCache<String, GridBucketState> cache = cacheManager.<String, GridBucketState>getCache("rateLimit").getAdvancedCache();
        FunctionalMapImpl<String, GridBucketState> functionalMap = FunctionalMapImpl.create(cache);
        return ReadWriteMapImpl.create(functionalMap);
    }
}