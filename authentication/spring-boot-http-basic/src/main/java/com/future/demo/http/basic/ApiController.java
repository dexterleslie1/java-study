package com.future.demo.http.basic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
public class ApiController {

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value="/hello")
    public ResponseEntity<String> hello(
            HttpServletRequest request,
            HttpServletResponse response){
        return ResponseEntity.ok("Hello Dexter");
    }
}
