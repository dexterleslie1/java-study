package com.future.demo.security.uaa.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Data
@TableName(value = "user")
public class UserDetailsModel implements UserDetails {
    @TableId(value = "id")
    private Long id;
    private String username;
    private String password;
    private String authorities;
    private Date createTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> setTemporary = new HashSet<>();
        if(!StringUtils.isEmpty(this.authorities)) {
            setTemporary = Arrays.stream(this.authorities.split(",")).collect(Collectors.toSet());
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(!CollectionUtils.isEmpty(setTemporary)) {
            setTemporary.forEach((authority)->{
                grantedAuthorities.add(new SimpleGrantedAuthority(authority));
            });
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
