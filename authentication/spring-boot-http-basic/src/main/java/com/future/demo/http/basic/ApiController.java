package com.future.demo.http.basic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
public class ApiController {

    /**
     *
     * @return
     */
    @PostMapping(value="/hello")
    public ResponseEntity<ObjectResponse> hello(@RequestParam(name = "name") String name){
        ObjectResponse<String> response = new ObjectResponse<>();
        response.setData("Hello " + name);
        return ResponseEntity.ok(response);
    }
}
