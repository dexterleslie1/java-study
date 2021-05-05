package com.future.demo.axios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ApiController {
    @Value("${spring.application.name}")
    String applicationName;

    @GetMapping("get")
    ResponseEntity<String> get(@RequestParam(value = "name", defaultValue = "") String name) {
        return ResponseEntity.ok("服务器" + applicationName + "向你打招呼：\"你好，" + name + "\"");
    }
}
