package com.future.demo.architecture.zuul;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/zuul")
public class ApiController {
    @GetMapping("test1")
    public ResponseEntity<String> test1() {
        return ResponseEntity.ok("成功调用zuul test1接口");
    }
}
