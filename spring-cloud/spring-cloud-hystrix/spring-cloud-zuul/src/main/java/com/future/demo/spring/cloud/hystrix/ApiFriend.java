package com.future.demo.spring.cloud.hystrix;

import com.future.demo.spring.cloud.common.ObjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spring-cloud-friend", fallbackFactory = ApiFriendFallbackFactory.class)
public interface ApiFriend {

    @PostMapping("/api/v1/friend/timeout")
    ResponseEntity<ObjectResponse<String>> timeout(@RequestParam(value = "milliseconds") Integer milliseconds);

    @PostMapping("/api/v1/friend/timeout2")
    ResponseEntity<ObjectResponse<String>> timeout2(@RequestParam(value = "milliseconds") Integer milliseconds);

    class ApiFriendFallback implements ApiFriend {
        @Override
        public ResponseEntity<ObjectResponse<String>> timeout(Integer milliseconds) {
            ObjectResponse<String> response = new ObjectResponse<>();
            response.setErrorCode(600);
            response.setErrorMessage("Friend服务不可用，稍候...（来自ApiFriend）");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        @Override
        public ResponseEntity<ObjectResponse<String>> timeout2(Integer milliseconds) {
            ObjectResponse<String> response = new ObjectResponse<>();
            response.setErrorCode(600);
            response.setErrorMessage("Friend服务不可用，稍候...（来自ApiFriend）");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }
}
