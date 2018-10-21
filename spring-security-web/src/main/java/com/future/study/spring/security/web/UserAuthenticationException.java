package com.future.study.spring.security.web;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Dexterleslie.Chan
 */
public class UserAuthenticationException extends AuthenticationException {
    /**
     *
     * @param msg
     */
    public UserAuthenticationException(String msg) {
        super(msg);
    }
}
