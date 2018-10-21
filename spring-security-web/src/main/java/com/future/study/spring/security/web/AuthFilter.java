package com.future.study.spring.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dexterleslie.Chan
 */
public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ticket=request.getParameter("ticket");

        UserAuthentication authentication=new UserAuthentication("user1","123456");
        authentication.setAuthenticated(true);

        if(authentication!=null&&authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);
        }else{
            Map<String,Object> mapReturn=new HashMap<>();
            mapReturn.put("errorCode",50001);
            mapReturn.put("errorMessage","您未登陆");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(mapReturn));
        }
    }
}
