package com.future.demo.java.spring.handler.argument.request.body;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
@RequestMapping(path = "/api/v1/handler/argument")
public class TestController {
    /**
     * 测试方法使用postman
     * body数据：body标签页选择 raw 并填写body内容，contentType选择application/json
     *
     * @param string
     * @return
     */
    @RequestMapping(path = "request/bodyWithString")
    public ResponseEntity<String> bodyWithString(
            @RequestBody(required = false) String string) {
        String responseString = "bodyString=" + string;
        return ResponseEntity.ok(responseString);
    }

    /**
     * 测试方法使用postman
     * body数据：body标签选择 raw 并填写body内容，contentType选择application/json
     * parameter数据：使用queryString提供key-value，例如 http://localhost:8080/api/v1/handler/argument/request/bodyAndParameter?parameter=p1
     *
     * @param string
     * @param parameter
     * @return
     */
    @RequestMapping(path = "request/bodyAndParameter")
    public ResponseEntity<String> bodyAndParameter(
            @RequestBody(required = false) String string,
            @RequestParam(name = "parameter", required = false) String parameter) {
        String responseString = "bodyString=" + string + ",parameter=" + parameter;
        return ResponseEntity.ok(responseString);
    }

    /**
     * 测试方法使用postman
     * body数据：body标签选择raw并填写body内容为[{"userIdTo":1,"receiptId":"r1"},{"userIdTo":2,"receiptId":"r2"}]，contentType选择application/json
     *
     * @param paramMessageVOList
     * @return
     */
    @RequestMapping(path = "request/bodyWithObject")
    public ResponseEntity<String> bodyWithObject(
            @RequestBody(required = false) List<ParamMessageVO> paramMessageVOList) {
        String responseString = "bodyJson=" + (paramMessageVOList!=null?paramMessageVOList.toString():null);
        return ResponseEntity.ok(responseString);
    }
}
