package com.future.study.http.send.large.json;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dexterleslie.Chan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {ApplicationBooter.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DemoTests {
    private RestTemplate restTemplate=new RestTemplate();

    @Test
    public void test1(){
        StringBuilder builder=new StringBuilder();
        byte []bytes=new byte[1024];
        Random random=new Random();
        for(int i=0;i<60*1024;i++){
            random.nextBytes(bytes);
            String base64= Base64Utils.encodeToString(bytes);
            builder.append(base64);
        }
        String url="http://localhost:8080/demo/upload1";
        LinkedMultiValueMap<String,String> valueMap=new LinkedMultiValueMap<>();
        valueMap.add("data",builder.toString());
        HttpEntity<LinkedMultiValueMap<String,String>> entity = new HttpEntity<LinkedMultiValueMap<String,String>>(valueMap, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, String.class);
        String body=response.getBody();
        System.out.println("body response="+body);
    }

    @Test(expected = SocketException.class)
    public void test2() throws IOException {
        StringBuilder builder=new StringBuilder();
        byte []bytes=new byte[1024];
        Random random=new Random();
        for(int i=0;i<10*1024;i++){
            random.nextBytes(bytes);
            String base64= Base64Utils.encodeToString(bytes);
            builder.append(base64);
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/demo/upload1");
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("data", builder.toString()));
//        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(nvps);
        StringEntity entity=new StringEntity(builder.toString());
        httpPost.setEntity(entity);
        CloseableHttpResponse response1 = httpclient.execute(httpPost);
        // The underlying HTTP connection is still held by the response object
        // to allow the response content to be streamed directly from the network socket.
        // In order to ensure correct deallocation of system resources
        // the user MUST call CloseableHttpResponse#close() from a finally clause.
        // Please note that if response content is not fully consumed the underlying
        // connection cannot be safely re-used and will be shut down and discarded
        // by the connection manager.
        try {
            System.out.println(response1.getStatusLine());
            org.apache.http.HttpEntity entity1 = response1.getEntity();
            String response=EntityUtils.toString(entity1);
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }
    }

    @Test
    public void test3() throws IOException {
        StringBuilder builder=new StringBuilder();
        byte []bytes=new byte[1024];
        Random random=new Random();
        for(int i=0;i<10*1024;i++){
            random.nextBytes(bytes);
            String base64= Base64Utils.encodeToString(bytes);
            builder.append(base64);
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/demo/upload1");
        MultipartEntityBuilder builder1 = MultipartEntityBuilder.create();
        builder1.addTextBody("data", builder.toString());
        org.apache.http.HttpEntity multipart = builder1.build();
        httpPost.setEntity(multipart);
        CloseableHttpResponse response1 = httpclient.execute(httpPost);
        try {
            System.out.println(response1.getStatusLine());
            org.apache.http.HttpEntity entity1 = response1.getEntity();
            String response=EntityUtils.toString(entity1);
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }
    }
}
