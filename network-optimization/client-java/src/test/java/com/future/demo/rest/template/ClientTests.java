package com.future.demo.rest.template;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ClientTests {
    final static String RestHost = "xxxx";
    final static int    RestPortWithoutCompression = 0;
    final static int    RestPortWithCompression = 0;
//    final static String RestHost = "localhost";
//    final static int    RestPort = 8081;

    @Autowired
    private RestTemplate restTemplate = null;

    /**
     * 测试对比HTTP header Connection: keepalive和Connection: close
     * 经过对比Connection: keepalive效率高于Connection: close
     */
    @Test
    public void testConnectionKeepaliveVersusConnectionClose(){
        // 测试前热身代码
        testConnectionHeader(RestHost, RestPortWithoutCompression, false, true);

        testConnectionHeader(RestHost, RestPortWithoutCompression, false, false);
        testConnectionHeader(RestHost, RestPortWithoutCompression, true, false);
    }

    /**
     *
     * @param host
     * @param port
     * @param isClose
     * @param isWarmup
     */
    void testConnectionHeader(String host, int port, boolean isClose, boolean isWarmup) {
        long startTime = System.currentTimeMillis();
        for(int i=1; i<=100; i++) {
            ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
            objectNode.put("name", "Dexter" + i);
            objectNode.put("age", 11);
            String json = objectNode.toString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if(isClose) {
                headers.setConnection("close");
            }
            HttpEntity httpEntity = new HttpEntity<>(json, headers);
            ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://" + host + ":" + port + "/api/v1/postBody", HttpMethod.POST, httpEntity, String.class);
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            String response = responseEntity.getBody();
            String message = "你提交的json：" + json;
            Assert.assertEquals(message, response);
        }
        long endTime = System.currentTimeMillis();
        long milliseconds = endTime - startTime;
        if(!isWarmup) {
            System.out.println("连接 " + host + ":" + port + " Connection: " + (isClose ? "close" : "keepalive") + " 耗时 " + milliseconds + " 毫秒");
        }
    }

    /**
     * 测试对比是否有compression
     * 对于少量数据没有compression快于有compression
     */
    @Test
    public void testCompression() {
        // 测试前热身代码
        testConnectionHeader(RestHost, RestPortWithoutCompression, false, true);
        testConnectionHeader(RestHost, RestPortWithCompression, false, true);

        testConnectionHeader(RestHost, RestPortWithoutCompression, false, false);
        testConnectionHeader(RestHost, RestPortWithCompression, false, false);
    }

    /**
     * 测试下载文件，对比是否有compression
     * 测试结果：有compression明显快于没有compression
     */
    @Test
    public void testDownloadFileCompression() throws UnsupportedEncodingException {
        testDownloadFile(RestHost, RestPortWithoutCompression, true, 2);
        testDownloadFile(RestHost, RestPortWithCompression, true, 2);

        testDownloadFile(RestHost, RestPortWithoutCompression, false, 10);
        testDownloadFile(RestHost, RestPortWithCompression, false, 10);
    }

    void testDownloadFile(String host, int port, boolean isWarmup, int count) throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        for(int i=0; i<count; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
            headers.add("Accept-Encoding", "gzip");
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(null, headers);
            String url = "http://" + host + ":" + port + "/api/v1/downloadFile";
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, byte[].class);
            if (!response.getHeaders().getContentType().equals(MediaType.APPLICATION_OCTET_STREAM)) {
                String resposneString = new String(response.getBody(), "utf-8");
                System.out.println(resposneString);
            } else {
                System.out.println("下载大小：" + (response.getBody().length / 1024) + "kb");
            }
        }
        long endTime = System.currentTimeMillis();
        long milliseconds = endTime - startTime;
        if(!isWarmup) {
            System.out.println("连接 " + host + ":" + port + " 下载文件耗时 " + milliseconds + " 毫秒");
        }
    }

    /**
     * 测试上传文件
     */
    @Test
    public void testUploadFile() throws IOException {
//        File file = prepareFile();
//        FileSystemResource fileSystemResource = new FileSystemResource(file);
//
//        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
//        parameters.add("file1",fileSystemResource);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<>(parameters, headers);
//        String url = "http://" + RestHost+ ":" + RestPort + "/api/v1/uploadFile";
//        ResponseEntity<byte[]> response = restTemplate.exchange(
//                url, HttpMethod.POST, entity, byte[].class);
//        if(!response.getHeaders().getContentType().equals(MediaType.APPLICATION_OCTET_STREAM)) {
//            String resposneString = new String(response.getBody(), "utf-8");
//            System.out.println(resposneString);
//        }
    }

    File prepareFile() throws IOException {
        String temporaryDirectory = System.getProperty("java.io.tmpdir");
        String filename = temporaryDirectory + "network-optimization.tmp";
        File file = new File(filename);
        if(!file.exists()) {
            file.createNewFile();
            byte [] bytes = new byte[1024];
            Random random = new Random();
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                for(int i=0; i<1024; i++) {
                    random.nextBytes(bytes);
                    outputStream.write(bytes, 0, bytes.length);
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                if(outputStream!=null) {
                    outputStream.close();
                    outputStream = null;
                }
            }
        }
        return file;
    }
}
