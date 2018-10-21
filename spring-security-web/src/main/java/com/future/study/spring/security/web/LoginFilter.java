package com.future.study.spring.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Dexterleslie.Chan
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     *
     * @param requiresAuthenticationRequestMatcher
     */
    protected LoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String loginname=request.getParameter("loginname");
        String password=request.getParameter("password");
        if("user1".equals(loginname)&&"123456".equals(password)) {
            Authentication authentication = new UserAuthentication(loginname, password);
            authentication.setAuthenticated(true);
            return authentication;
        }
        throw new UserAuthenticationException("账号或者密码错误");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        Map<String,Object> mapReturn=new HashMap<>();
        mapReturn.put("userId",4738438);
        mapReturn.put("loginname",authResult.getName());
        mapReturn.put("ticket", UUID.randomUUID().toString());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(mapReturn));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String,Object> mapReturn=new HashMap<>();
        mapReturn.put("errorCode",50000);
        mapReturn.put("errorMessage",failed.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(mapReturn));
    }
}
