package com.future.demo.architecture.zuul;

import com.future.demo.architecture.common.feign.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/zuul")
public class ApiController {
    @Autowired
    HelloClient helloClient;

    @GetMapping(value = "test1", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> test1() {
        return ResponseEntity.ok("成功调用zuul test1接口");
    }

    @PostMapping(value = "test2", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> test2(@RequestParam(value = "param1", defaultValue = "") String param1) {
        return helloClient.test2(param1);
    }
}
