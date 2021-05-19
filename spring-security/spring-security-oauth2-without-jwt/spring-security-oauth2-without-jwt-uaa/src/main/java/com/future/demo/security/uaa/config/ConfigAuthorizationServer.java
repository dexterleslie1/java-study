package com.future.demo.security.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class ConfigAuthorizationServer extends AuthorizationServerConfigurerAdapter {
    // 所有客户端密码
    private final static String ClientSecret = "123";

    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    // 配置token存放在redis中
    @Bean
    TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    // 配置客户端详情服务(ClientDetailsService),ClientDetailsService负责查找ClientDetails
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client1")
                .secret(passwordEncoder.encode(ClientSecret))
//                .resourceIds("resource1")
                // authorization_code 授权码模式，，跳转到登录页面需要用户登录后并授权后才能够获取token
                // implicit 静默授权模式，跳转到登录页面需要用户登录后并授权才能够获取token
                .authorizedGrantTypes("authorization_code", "implicit")
                .scopes("all")
                .redirectUris("http://www.baidu.com")

                .and()
                .withClient("client2")
                .secret(passwordEncoder.encode(ClientSecret))
//                .resourceIds("resouce1")
                // password 密码模式，用户提供账号密码后不需要登录授权直接获取token
                .authorizedGrantTypes("password")
                .scopes("write")

                .and().withClient("client3")
                .secret(passwordEncoder.encode(ClientSecret))
//                .resourceIds("resource1")
                // client_credentails 客户端模式，不需要提供用户账号密码信息即可获取token
                .authorizedGrantTypes("client_credentials")
                .scopes("all")

                // order-service客户端
                .and().withClient("order-service-resource")
                .secret(passwordEncoder.encode(ClientSecret))
                .authorizedGrantTypes("client_credentials")
                .scopes("all");
    }

    // 配置令牌访问端点
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                // 密码模式，用户提供账号密码不需要登录直接校验通过并获取token
                .authenticationManager(authenticationManager);
    }

    // 配置授权服务器端点安全
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
//                // 对应/oauth/token_key端点
//                .tokenKeyAccess("permitAll()")
                // 访问/oauth/check_token端点允许所有客户端
                .checkTokenAccess("permitAll()");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
