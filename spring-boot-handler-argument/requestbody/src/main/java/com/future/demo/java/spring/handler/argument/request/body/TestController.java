package com.future.demo.java.spring.handler.argument.request.body;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
@RequestMapping(path = "/api/v1/handler/argument/request")
public class TestController {
    /**
     * 测试方法使用postman
     * body数据：body标签页选择 raw 并填写body内容为 {"age":10,"name":"Dexter1","salary":20.1}，contentType选择application/json
     * @param parameters
     * @return
     */
    @RequestMapping(path = "bodyAsMap")
    ResponseEntity<String> bodyAsMap(
            @RequestBody(required = false) Map<String, Object> parameters) {
        int age = (Integer)parameters.get("age");
        String name = (String)parameters.get("name");
        double salary = (Double)parameters.get("salary");
        System.out.println("age=" + age + ",name=" + name + ",salary=" + salary);
        Date currentTime = new Date();
        return ResponseEntity.ok(String.valueOf(currentTime));
    }

    /**
     * 测试方法使用postman
     * body数据：body标签页选择 raw 并填写body内容为 [{"age":10,"name":"Dexter1","salary":20.1},{"age":11,"name":"Dexter2","salary":20.2}]，contentType选择application/json
     * @param parameters
     * @return
     */
    @RequestMapping(path = "bodyAsListWithEntryMap")
    ResponseEntity<String> bodyAsListWithEntryMap(
        @RequestBody(required = false) List<Map<String, Object>> parameters) {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<parameters.size(); i++) {
            Map<String, Object> parameter = parameters.get(i);
            int age = (Integer)parameter.get("age");
            String name = (String)parameter.get("name");
            double salary = (Double)parameter.get("salary");
            builder.append("age=" + age + ",name=" + name + ",salary=" + salary);
            builder.append("\n");
        }
        System.out.println(builder.toString());
        Date currentTime = new Date();
        return ResponseEntity.ok(String.valueOf(currentTime));
    }

    /**
     * 测试方法使用postman
     * body数据：body标签页选择 raw 并填写body内容，contentType选择application/json
     *
     * @param string
     * @return
     */
    @RequestMapping(path = "bodyWithString")
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
    @RequestMapping(path = "bodyAndParameter")
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
    @RequestMapping(path = "bodyWithObject")
    public ResponseEntity<String> bodyWithObject(
            @RequestBody(required = false) List<ParamMessageVO> paramMessageVOList) {
        String responseString = "bodyJson=" + (paramMessageVOList!=null?paramMessageVOList.toString():null);
        return ResponseEntity.ok(responseString);
    }
}
