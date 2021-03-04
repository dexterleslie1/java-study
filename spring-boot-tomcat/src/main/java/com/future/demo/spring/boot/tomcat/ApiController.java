package com.future.demo.spring.boot.tomcat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @PostMapping("timeout")
    ResponseEntity<String> timeout(@RequestParam(value = "timeout", defaultValue = "0") Integer timeout) {
        if(timeout>0) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                //
            }
        }
        return ResponseEntity.ok("成功调用timeout接口");
    }
}
