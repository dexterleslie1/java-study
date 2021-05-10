package com.future.demo.security.authorization.config;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class Config extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用csrf
        http.csrf().disable();

        http.authorizeRequests()
                // 不拦截login.html页面请求
                .antMatchers("/login.html").permitAll()
                // 不拦截loginFailed.html页面请求
                .antMatchers("/loginFailed.html").permitAll()
                // 拥有admin:view权限才能够访问admin.html页面
                .antMatchers("/admin.html").hasAuthority("admin:view")
//                // 拥有admin角色才能够访问admin.html页面
//                .antMatchers("/admin.html").hasRole("admin")
                // 使用access表达式实现上面等价hasRole("admin")
                .antMatchers("/admin.html").access("hasRole('admin')")
                // 自定义access权限访问控制
                .antMatchers("/hasPermission.html").access("@hasPermissionService.hasPermission(request,authentication)")
                // 拦截所有未登录url
                .anyRequest().authenticated();

        http.formLogin()
                // 自定义登录表单username、password参数名称
                .usernameParameter("usernameDemo")
                .passwordParameter("passwordDemo")
                // 自定义登录页面
                .loginPage("/login.html")
                // 自定义登录url，此值要和login.html登录表单action一致
                .loginProcessingUrl("/login")
                // 登录成功后跳转url，最终会跳转到index.html
                .successForwardUrl("/index")
                // 登录失败跳转url，最终会跳转到loginFailed.html
                .failureForwardUrl("/loginFailed");

        // 禁止访问自定义403返回
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");

                PrintWriter writer = null;
                try {
                    writer = httpServletResponse.getWriter();
                    ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
                    objectNode.put("errorCode", "500001");
                    objectNode.put("errorMessage", "权限不足，联系客服");
                    writer.write(objectNode.toString());
                } catch (Exception ex) {
                    throw ex;
                } finally {
                    if(writer!=null) {
                        writer.flush();
                        writer.close();
                    }
                }
            }
        });

        // 退出登录设置
        http.logout().logoutSuccessUrl("/login.html");
    }

    // 密码编码器
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}