package com.future.demo.spring.cloud.hystrix;

import com.future.demo.spring.cloud.common.ObjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/user")
public class ApiControllerUser {
    @PostMapping("timeout")
    ResponseEntity<ObjectResponse<String>> timeout(@RequestParam(value = "milliseconds", defaultValue = "0") int milliseconds) {
        if(milliseconds>0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException e) {
                //
            }
        }

        ObjectResponse<String> response = new ObjectResponse<>();
        response.setData("成功调用timeout接口");
        return ResponseEntity.ok(response);
    }
}
