package com.future.study.ngrok;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class Config {
    @Bean
    public OncePerRequestFilter httpTraceFilter(){
        return new HttpTraceFilter();
    }
}
