package com.future.demo.chat.server.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * Spring HttpEntityUtils工具
 */
public class HttpEntityUtils {
    /**
     * 根据指定参数列表构造HttpEntity
     * @param params
     * @return
     */
    public static HttpEntity<MultiValueMap<String, String>> createHttpEntity(Map<String, String> params){
        MultiValueMap<String, String> multiValueParams= new LinkedMultiValueMap<>();
        if(params != null) {
            for(String key : params.keySet()) {
                multiValueParams.add(key, params.get(key));
            }
        }
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueParams, null);
        return httpEntity;
    }

    /**
     * 根据指定参数列表构造HttpEntity
     * @param params
     * @param headers
     * @return
     */
    public static HttpEntity<MultiValueMap<String, String>> createHttpEntity(Map<String, String> params, Map<String, String> headers){
        MultiValueMap<String, String> multiValueParams= new LinkedMultiValueMap<>();
        if(params != null) {
            for(String key : params.keySet()) {
                multiValueParams.add(key, params.get(key));
            }
        }

        MultiValueMap<String, String> multiValueHeaders= new LinkedMultiValueMap<>();
        if(headers != null) {
            for(String key : headers.keySet()) {
                multiValueHeaders.add(key, headers.get(key));
            }
        }
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueParams, multiValueHeaders);
        return httpEntity;
    }

    /**
     * 根据指定参数列表构造HttpEntity
     * @param params
     * @param headers
     * @return
     */
    public static HttpEntity<MultiValueMap<String, String>> createHttpEntity(Map<String, String> params,
                                                                             HttpHeaders headers){
        MultiValueMap<String, String> multiValueParams= new LinkedMultiValueMap<>();
        if(params != null) {
            for(String key : params.keySet()) {
                multiValueParams.add(key, params.get(key));
            }
        }

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueParams, headers);
        return httpEntity;
    }

    /**
     * 根据指定参数列表构造HttpEntity，此方法支持文件上传
     * @param params
     * @param headers
     * @return
     */
    public static HttpEntity<MultiValueMap<String, Object>> createHttpEntitySupportFile(Map<String, Object> params,
                                                                             HttpHeaders headers){
        MultiValueMap<String, Object> multiValueParams= new LinkedMultiValueMap<>();
        if(params != null) {
            for(String key : params.keySet()) {
                multiValueParams.add(key, params.get(key));
            }
        }

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multiValueParams, headers);
        return httpEntity;
    }
}
