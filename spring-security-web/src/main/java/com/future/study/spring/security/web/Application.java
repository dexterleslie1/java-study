package com.future.study.spring.security.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author Dexterleslie
 * @date 2018年08月14日
 * @time 8:53
 */
@SpringBootApplication(
        exclude = {
                // 取消Security自动配置，否则在没有配置@EnableWebSecurity
                // 时security自动启动会默认拦截所有请求
                SecurityAutoConfiguration.class
        })
public class Application {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

}