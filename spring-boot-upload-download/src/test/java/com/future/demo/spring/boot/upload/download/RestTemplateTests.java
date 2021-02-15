package com.future.demo.spring.boot.upload.download;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class RestTemplateTests {
    /**
     *
     */
    @Test
    public void testUpload() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String content = UUID.randomUUID().toString();
        File file = createFileIfNotExists(content);
        FileSystemResource fileSystemResource = new FileSystemResource(file);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file1",fileSystemResource);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<>(parameters, headers);
        String url = "http://localhost:8080/api/v1/upload";
        ResponseEntity<byte[]> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, byte[].class);
        if(!response.getHeaders().getContentType().equals(MediaType.APPLICATION_OCTET_STREAM)) {
            String resposneString = new String(response.getBody(), "utf-8");
            System.out.println(resposneString);
        }
    }

    /**
     *
     */
    @Test
    public void testDownload() throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<>(null, headers);
        String url = "http://localhost:8080/api/v1/download";
        ResponseEntity<byte[]> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, byte[].class);
        if(!response.getHeaders().getContentType().equals(MediaType.APPLICATION_OCTET_STREAM)) {
            String resposneString = new String(response.getBody(), "utf-8");
            System.out.println(resposneString);
        } else {
            String resposneString = new String(response.getBody(), "utf-8");
            System.out.println("下载文件内容：" + resposneString);
        }
    }

    final static String TemporaryDirectory=System.getProperty("java.io.tmpdir");
    File createFileIfNotExists(String content) throws IOException {
        String filename = TemporaryDirectory + "spring-boot-upload-download-upload-file.tmp";
        File file = new File(filename);
        if(!file.exists()) {
            file.createNewFile();
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                outputStream.write(content.getBytes("utf-8"));
            } catch (Exception ex) {
                throw ex;
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            }
        }
        return file;
    }
}
