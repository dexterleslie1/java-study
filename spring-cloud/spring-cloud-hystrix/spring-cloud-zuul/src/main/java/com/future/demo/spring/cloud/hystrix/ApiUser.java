package com.future.demo.spring.cloud.hystrix;

import com.future.demo.spring.cloud.common.ListResponse;
import com.future.demo.spring.cloud.common.ObjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spring-cloud-user", fallback = ApiUser.ApiUserFallback.class/* TODO: 使用fallbackFactory实现 */)
// NOTE: 千万不能再Feign interface上添加@RequestMapping，否则报告There is already 'XXXXX' bean method错误
// https://blog.csdn.net/weixin_44495198/article/details/105931661
//@RequestMapping("/api/v1")
public interface ApiUser {

    @PostMapping("/api/v1/user/timeout")
    ResponseEntity<ObjectResponse<String>> timeout(@RequestParam(value = "milliseconds") Integer milliseconds);

    @Component
    class ApiUserFallback implements ApiUser{
        @Override
        public ResponseEntity<ObjectResponse<String>> timeout(Integer milliseconds) {
            ObjectResponse<String> response = new ObjectResponse<>();
            response.setErrorCode(600);
            response.setErrorMessage("User服务不可用，稍候...（来自ApiUser）");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }
}
