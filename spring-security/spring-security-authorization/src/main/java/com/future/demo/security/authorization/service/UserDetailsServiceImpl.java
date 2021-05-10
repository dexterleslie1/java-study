package com.future.demo.security.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!"admin".equals(username) && !"user".equals(username)) {
            throw new UsernameNotFoundException("用户" + username + "不存在");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)) {
            // 新增admin:view权限
            authorities.add(new SimpleGrantedAuthority("admin:view"));
            // 新增admin角色
            authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
            // 自定义access权限访问控制@hasPermissionService.hasPermission(request,authentication)
            authorities.add(new SimpleGrantedAuthority("/hasPermission.html"));
        } else {
            authorities.add(new SimpleGrantedAuthority("user"));
        }

        String encodedPassword = passwordEncoder.encode("123");
        return new User(username, encodedPassword, authorities);
    }
}
