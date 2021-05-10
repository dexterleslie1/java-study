package com.future.demo.security.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(
        securedEnabled = true,/* 开启secured注解判断是否拥有角色 */
        prePostEnabled = true/* 开启preAuthorize注解 */)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}