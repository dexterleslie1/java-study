package com.future.demo.architecture.hello.world;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping(value = "/api/v1/sayHello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHello(@RequestParam(value = "name", defaultValue = "") String name) {
        return "Hello " + name + "!!(Zuul)";
    }

    @GetMapping(value = "/api/v1/test1", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> test1() {
        return ResponseEntity.ok("成功调用helloworld test1接口");
    }

    @PostMapping(value = "/api/v1/test2", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> test2(@RequestParam(value = "param1", defaultValue = "") String param1) {
        return ResponseEntity.ok("你的请求参数param1=" + param1);
    }
}
