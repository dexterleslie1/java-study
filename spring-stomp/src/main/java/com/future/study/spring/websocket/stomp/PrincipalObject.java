package com.future.study.spring.websocket.stomp;

import java.security.Principal;

/**
 * @author Dexterleslie.Chan
 */
public class PrincipalObject implements Principal {
    private String username;

    /**
     *
     * @param username
     */
    PrincipalObject(String username){
        this.username=username;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return this.username;
    }
}