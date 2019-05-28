package com.future.study.spring.websocket.stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedList;

/**
 *
 */
@Configuration
@EnableWebSecurity
public class ConfigSpringSecurity extends WebSecurityConfigurerAdapter {
    /**
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        LinkedList<RequestMatcher> matcherList = new LinkedList<>();
        matcherList.add(new AntPathRequestMatcher("/**"));
        web.ignoring().requestMatchers(new OrRequestMatcher(matcherList));
    }

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().permitAll();
    }
}
