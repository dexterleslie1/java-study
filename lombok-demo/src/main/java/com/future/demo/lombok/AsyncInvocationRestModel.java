package com.future.demo.lombok;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 *
 */
@Getter
@SuperBuilder
public class AsyncInvocationRestModel extends AsyncInvocationModel{
    private String authenticationBasicUser;
    private String authenticationBasicPassword;
    private String host;
    private int port;
    private String uri;
}
