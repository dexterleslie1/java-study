package com.future.demo.java.spring.handler.argument.path.variable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
@RequestMapping(path = "/api/{version}/handler/argument")
public class TestController {
    /**
     * 使用如下url访问
     * http://localhost:8080/api/%20/handler/argument/path/variable/%20
     * http://localhost:8080/api/v1/handler/argument/path/variable/330
     * @param version
     * @param parameter
     * @return
     */
    @RequestMapping(path = "path/variable/{parameter}")
    public ResponseEntity<String> api1(
            @PathVariable(name = "version", required = false) String version,
            @PathVariable(name = "parameter", required = false) Long parameter) {
        String string = "version=" + version + ",parameter=" + parameter;
        return ResponseEntity.ok(string);
    }
}
