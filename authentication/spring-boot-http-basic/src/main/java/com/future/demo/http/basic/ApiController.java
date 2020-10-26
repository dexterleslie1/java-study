package com.future.demo.http.basic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity<String> hello(@RequestParam(name = "name") String name){
        return ResponseEntity.ok("Hello " + name);
    }
}
