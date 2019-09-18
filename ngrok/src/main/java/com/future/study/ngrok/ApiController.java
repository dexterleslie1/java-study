package com.future.study.ngrok;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/")
public class ApiController {
    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);


    /**
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "event")
    public ResponseEntity<String> event(
            HttpServletRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok("Hello");
    }
}