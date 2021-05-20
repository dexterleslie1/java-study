package com.future.demo.security.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
// order-service作为资源服务器
@EnableResourceServer
public class ConfigResourceServer {
}
