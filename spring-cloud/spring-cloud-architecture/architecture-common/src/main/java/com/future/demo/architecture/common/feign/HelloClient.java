package com.future.demo.architecture.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "architecture-helloworld")
public interface HelloClient {
    @PostMapping(value = "/api/v1/test2", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> test2(@RequestParam(value = "param1") String param1);
}
