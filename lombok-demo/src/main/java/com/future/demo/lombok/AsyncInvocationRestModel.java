package com.future.demo.lombok;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 */
@Getter
@SuperBuilder
@NoArgsConstructor
public class AsyncInvocationRestModel extends AsyncInvocationModel{
    private String authenticationBasicUser;
    private String authenticationBasicPassword;
    private String host;
    private int port;
    private String uri;

    @Builder.Default
    private AsyncInvocationType type = AsyncInvocationType.REST;
}
