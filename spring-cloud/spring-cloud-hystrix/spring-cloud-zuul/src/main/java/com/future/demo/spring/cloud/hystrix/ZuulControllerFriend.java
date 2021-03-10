package com.future.demo.spring.cloud.hystrix;

import com.future.demo.spring.cloud.common.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friend")
public class ZuulControllerFriend {
    @Autowired
    ApiFriend apiFriend;
    @Autowired
    ApiFriendRestTemplate apiFriendRestTemplate;

    @PostMapping("timeoutWithFeignFallback")
    ResponseEntity<ObjectResponse<String>> timeoutWithFeignFallback(@RequestParam(value = "milliseconds") Integer milliseconds) {
        return this.apiFriend.timeout(milliseconds);
    }

    @PostMapping("timeoutWithFeignFallback2")
    ResponseEntity<ObjectResponse<String>> timeoutWithFeignFallback2(@RequestParam(value = "milliseconds") Integer milliseconds) {
        return this.apiFriend.timeout2(milliseconds);
    }

    @PostMapping("timeoutWithRestTemplate")
    ResponseEntity<ObjectResponse<String>> timeoutWithRestTemplate(@RequestParam(value = "milliseconds") Integer milliseconds) {
        return this.apiFriendRestTemplate.timeout(milliseconds);
    }

    @PostMapping("timeoutWithRestTemplate2")
    ResponseEntity<ObjectResponse<String>> timeoutWithRestTemplate2(@RequestParam(value = "milliseconds") Integer milliseconds) {
        return this.apiFriendRestTemplate.timeout2(milliseconds);
    }
}
