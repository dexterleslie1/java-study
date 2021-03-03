package com.future.demo.spring.cloud.zuul;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
public class ApiController {
    /**
     *
     * @param name
     * @return
     */
    @GetMapping("/api/v1/sayHello")
    public String sayHello(@RequestParam(value = "name", defaultValue = "") String name) {
        return "Hello " + name + "!!(Zuul)";
    }

    @GetMapping("/api/v1/test1")
    public ResponseEntity<String> test1() {
        return ResponseEntity.ok("成功调用helloworld test1接口");
    }
}
