package com.future.demo.security.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
// order-service作为资源服务器
@EnableResourceServer
public class ConfigResourceServer {
    @Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        // order-service请求uaa服务客户端信息
        tokenServices.setClientId("order-service-resource");
        tokenServices.setClientSecret("123");
        // uaa服务check_token端点
        tokenServices.setCheckTokenEndpointUrl("http://localhost:9999/oauth/check_token");
        return tokenServices;
    }
}
