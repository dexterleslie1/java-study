package com.future.demo.spring.boot.dev.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    @Value("${spring.application.name}")
    String applicationName;

    @PostMapping("test")
    ResponseEntity<String> test(@RequestParam(value = "name", defaultValue = "") String name) {
        return ResponseEntity.ok("服务器" + applicationName + "向你打招呼：\"你好，" + name + "\"");
    }
}
