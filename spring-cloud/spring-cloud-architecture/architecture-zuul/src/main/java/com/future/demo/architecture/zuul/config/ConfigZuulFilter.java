package com.future.demo.architecture.zuul.config;

import com.future.demo.architecture.zuul.filter.ZuulFilterTesting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigZuulFilter {
    @Bean
    ZuulFilterTesting zuulFilterTesting() {
        ZuulFilterTesting zuulFilterTesting = new ZuulFilterTesting();
        return zuulFilterTesting;
    }
}
