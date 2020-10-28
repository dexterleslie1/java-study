package com.future.demo.lombok;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AsyncInvocationTests {
    /**
     *
     */
    @Test
    public void test() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("param1", "p1");
        String authenticationBasicUser = "user1";
        String host = "192.168.1.1";
        int port = 8091;
        String uri = "/api/v1/user/login";

        // 测试创建RESTful类型AsyncInvocation
        AsyncInvocationRestModel asyncInvocationRestModel =
                AsyncInvocationRestModel.builder()
                        .parameters(parameters)
                        .authenticationBasicUser(authenticationBasicUser)
                        .host(host)
                        .port(port)
                        .uri(uri).build();

        Assert.assertEquals(parameters.get("param1"), asyncInvocationRestModel.getParameters().get("param1"));
        Assert.assertEquals(authenticationBasicUser, asyncInvocationRestModel.getAuthenticationBasicUser());
        Assert.assertEquals(host, asyncInvocationRestModel.getHost());
        Assert.assertEquals(port , asyncInvocationRestModel.getPort());
        Assert.assertEquals(uri, asyncInvocationRestModel.getUri());
        Assert.assertEquals(AsyncInvocationType.REST, asyncInvocationRestModel.getType());

        // 测试lombok json
        String json = asyncInvocationRestModel.toJson();
        AsyncInvocationRestModel asyncInvocationRestModelFromJson =
                (AsyncInvocationRestModel)AsyncInvocationModel.fromJson(json, AsyncInvocationRestModel.class);
        Assert.assertEquals(parameters.get("param1"), asyncInvocationRestModelFromJson.getParameters().get("param1"));
        Assert.assertEquals(authenticationBasicUser, asyncInvocationRestModelFromJson.getAuthenticationBasicUser());
        Assert.assertEquals(host, asyncInvocationRestModelFromJson.getHost());
        Assert.assertEquals(port , asyncInvocationRestModelFromJson.getPort());
        Assert.assertEquals(uri, asyncInvocationRestModelFromJson.getUri());
        Assert.assertEquals(AsyncInvocationType.REST, asyncInvocationRestModelFromJson.getType());
    }
}
