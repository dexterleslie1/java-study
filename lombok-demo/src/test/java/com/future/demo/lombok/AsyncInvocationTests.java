package com.future.demo.lombok;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
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
    public void test() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("param1", "p1");
        String authenticationBasicUser = "user1";
        String host = "192.168.1.1";
        int port = 8091;
        String uri = "/api/v1/user/login";

        // 测试创建RESTful类型AsyncInvocation
        AsyncInvocationRestModel asyncInvocationRestModel =
                AsyncInvocationRestModel.builder()
                        .type(AsyncInvocationType.REST)
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

        // 测试lombok json
        ObjectMapper OMInstance = new ObjectMapper();
        String json = OMInstance.writeValueAsString(asyncInvocationRestModel);
        JsonNode node = OMInstance.readTree(json);
        Assert.assertEquals(parameters.get("param1"), node.get("parameters").get("param1").asText());
        Assert.assertEquals(authenticationBasicUser, node.get("authenticationBasicUser").asText());
        Assert.assertEquals(host, node.get("host").asText());
        Assert.assertEquals(port , node.get("port").asInt());
        Assert.assertEquals(uri, node.get("uri").asText());
    }
}
