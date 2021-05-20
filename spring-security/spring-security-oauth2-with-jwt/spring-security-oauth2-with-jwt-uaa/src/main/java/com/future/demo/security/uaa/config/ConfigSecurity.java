package com.future.demo.security.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 配置内存用户用于登录
//        auth.inMemoryAuthentication()
//                .withUser("user1")
//                .password(passwordEncoder.encode(UserSecret))
//                .authorities("sys:admin")
//                .and()
//                .withUser("user2")
//                .password(passwordEncoder.encode(UserSecret))
//                .authorities("sys:nothing");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用csrf
        http.csrf().disable();
        // 开启form登录
        http.formLogin();
        // 所有请求都需要登录验证
        http.authorizeRequests().anyRequest().authenticated();
    }

    // 密码模式，用户提供账号密码不需要登录直接校验通过并获取token
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
