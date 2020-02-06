package com.future.demo.maven.dockerfile.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dexterleslie.Chan
 */
@RestController
public class ApiController {
    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value="/")
    public ResponseEntity<String> test1(
            HttpServletRequest request,
            HttpServletResponse response){
        logger.info("Api for testing is called.");
        return ResponseEntity.ok("你好，dockerfile-maven-plugin 演示");
    }
}
