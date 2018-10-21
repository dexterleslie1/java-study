package com.future.study.spring.security.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Dexterleslie.Chan
 */
@Configuration
public class MineFilterConfiguration {
    @Bean
    public OncePerRequestFilter mineFilter(){
        OncePerRequestFilter filter=new MineFilter();
        return filter;
    }
}
