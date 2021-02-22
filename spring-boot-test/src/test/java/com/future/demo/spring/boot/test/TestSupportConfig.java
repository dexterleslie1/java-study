package com.future.demo.spring.boot.test;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestSupportConfig {
    private TestService testService;

    public TestSupportConfig() {
        this.testService = Mockito.mock(TestService.class);
    }

    @Bean
    public TestService testService() {
        return this.testService;
    }
}
