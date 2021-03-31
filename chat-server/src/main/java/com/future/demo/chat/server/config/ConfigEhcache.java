package com.future.demo.chat.server.config;

import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 *
 */
@Configuration
public class ConfigEhcache {
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        String configLocation = "ehcache.xml";
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource(configLocation));
        return factoryBean;
    }
}
