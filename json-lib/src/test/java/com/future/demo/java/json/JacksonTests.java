package com.future.demo.java.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Jackson库测试
 * @author dexterleslie@gmail.com
 */
public class JacksonTests {
    private final static Logger log = LoggerFactory.getLogger(JacksonTests.class);

    /**
     *
     */
    @Test
    public void bean2json() throws IOException {
        long userId = 12345l;
        String loginname = "dexter";
        boolean enable = true;
        Date createTime = new Date();
        BeanClass beanClass = new BeanClass();
        beanClass.setUserId(userId);
        beanClass.setLoginname(loginname);
        beanClass.setEnable(enable);
        beanClass.setCreateTime(createTime);

        ObjectMapper OMInstance = new ObjectMapper();
        String json = OMInstance.writeValueAsString(beanClass);
        beanClass = OMInstance.readValue(json, BeanClass.class);
        Assert.assertEquals(userId, beanClass.getUserId());
        Assert.assertEquals(loginname, beanClass.getLoginname());
        Assert.assertEquals(false, beanClass.isEnable());
        Assert.assertEquals(createTime, beanClass.getCreateTime());
    }

    /**
     *
     */
    @Test
    public void json2bean() throws IOException {
        String json = "{\"userId\":12345,\"loginname\":\"dexter\",\"createTime\":1577874822420,\"enable\":true}";

        long userId = 12345l;
        String loginname = "dexter";
        Date createTime = new Date(1577874822420l);

        ObjectMapper OMInstance = new ObjectMapper();
        BeanClass beanClass = OMInstance.readValue(json, BeanClass.class);
        Assert.assertEquals(userId, beanClass.getUserId());
        Assert.assertEquals(loginname, beanClass.getLoginname());
        Assert.assertEquals(false, beanClass.isEnable());
        Assert.assertEquals(createTime, beanClass.getCreateTime());
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void json2JsonNode() throws IOException {
        String json = "{\"userId\":12345,\"loginname\":\"dexter\",\"createTime\":1577874822420,\"enable\":true}";
        ObjectMapper OMInstance = new ObjectMapper();
        JsonNode jsonNode = OMInstance.readTree(json);
        Assert.assertEquals(true, jsonNode.get("enable").asBoolean());
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void objectNode() throws IOException {
        ObjectMapper OMInstance = new ObjectMapper();
        ObjectNode objectNode = OMInstance.createObjectNode();
        objectNode.put("enable", true);
        objectNode.put("loginname", "dexter");

        String json = OMInstance.writeValueAsString(objectNode);
        JsonNode jsonNode = OMInstance.readTree(json);
        Assert.assertEquals(true, jsonNode.get("enable").asBoolean());
        Assert.assertEquals("dexter", jsonNode.get("loginname").asText());

        objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("enable", true);
        objectNode.put("loginname", "dexter");
        Assert.assertEquals(true, jsonNode.get("enable").asBoolean());
        Assert.assertEquals("dexter", jsonNode.get("loginname").asText());
    }
    
    /**
     *
     * @throws IOException
     */
    @Test
    public void jsonFormat() throws IOException {
        long userId = 12345l;
        String loginname = "dexter";
        boolean enable = true;
        Date createTime = new Date();
        BeanClass beanClass = new BeanClass();
        beanClass.setUserId(userId);
        beanClass.setLoginname(loginname);
        beanClass.setEnable(enable);
        beanClass.setCreateTime(createTime);

        String createTimeStringExpected = DateFormatUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");

        ObjectMapper OMInstance = new ObjectMapper();
        String json = OMInstance.writeValueAsString(beanClass);
        JsonNode jsonNode = OMInstance.readTree(json);
        String createTimeStringActual = jsonNode.get("createTime").asText();
        Assert.assertEquals(createTimeStringExpected, createTimeStringActual);
    }

    /**
     *
     * @throws JsonProcessingException
     */
    @Test
    public void prettyPrint() throws JsonProcessingException {
        long userId = 12345l;
        String loginname = "dexter";
        boolean enable = true;
        Date createTime = new Date();
        BeanClass beanClass = new BeanClass();
        beanClass.setUserId(userId);
        beanClass.setLoginname(loginname);
        beanClass.setEnable(enable);
        beanClass.setCreateTime(createTime);

        ObjectMapper OMInstance = new ObjectMapper();
        String json = OMInstance.writerWithDefaultPrettyPrinter().writeValueAsString(beanClass);
        log.info(json);
    }

    @Test
    public void jsonNodeToObjectNode() throws IOException {
        String json = "{\"userId\":12345,\"loginname\":\"dexter\",\"createTime\":1577874822420,\"enable\":true}";
        ObjectMapper OMInstance = new ObjectMapper();
        JsonNode jsonNode = OMInstance.readTree(json);
        ObjectNode objectNode = (ObjectNode)jsonNode;
        log.info(jsonNode.toString());
        log.info(objectNode.toString());
    }
}
