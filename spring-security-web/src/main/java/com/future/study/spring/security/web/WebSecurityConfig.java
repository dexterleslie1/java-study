package com.future.study.spring.security.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Dexterleslie.Chan
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/api/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new LoginFilter(new AntPathRequestMatcher("/api/auth/login")), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}