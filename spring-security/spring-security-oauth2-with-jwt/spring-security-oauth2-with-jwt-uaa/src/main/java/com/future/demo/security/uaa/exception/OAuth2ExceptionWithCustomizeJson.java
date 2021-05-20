package com.future.demo.security.uaa.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = OAuth2ExceptionJsonSerializer.class)
public class OAuth2ExceptionWithCustomizeJson extends OAuth2Exception {
    public OAuth2ExceptionWithCustomizeJson(String msg) {
        super(msg);
    }
}
