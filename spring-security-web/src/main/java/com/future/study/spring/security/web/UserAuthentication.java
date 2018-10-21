package com.future.study.spring.security.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Dexterleslie.Chan
 */
public class UserAuthentication implements Authentication {
    private String loginname;
    private String password;
    private boolean isAuthenticated=false;

    /**
     *
     * @param loginname
     * @param password
     */
    public UserAuthentication(String loginname,String password){
        this.loginname=loginname;
        this.password=password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.loginname;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated=isAuthenticated;
    }

    @Override
    public String getName() {
        return this.loginname;
    }
}
