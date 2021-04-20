package com.future.demo.spring.boot.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ApiController {
    @Autowired
    TestService testService;

    @GetMapping("add")
    void add(@RequestParam("a") int a,
             @RequestParam("b") int b) {
        int c = testService.add(a, b);
        log.debug("c=" + c);
    }
}
