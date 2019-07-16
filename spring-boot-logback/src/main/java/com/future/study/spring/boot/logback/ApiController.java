package com.future.study.spring.boot.logback;

import com.future.study.spring.boot.package1.Tester1;
import com.future.study.spring.boot.package2.Tester2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/api")
public class ApiController {
    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);
    /**
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value="test")
    public ResponseEntity<String> test(
            HttpServletRequest request,
            HttpServletResponse response){
        logger.debug("test method is called");
        return ResponseEntity.ok("");
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value="test1")
    public ResponseEntity<String> test1(
            HttpServletRequest request,
            HttpServletResponse response){
        Tester1 tester = new Tester1();
        tester.method();
        return ResponseEntity.ok("");
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value="test2")
    public ResponseEntity<String> test2(
            HttpServletRequest request,
            HttpServletResponse response){
        Tester2 tester = new Tester2();
        tester.method();
        return ResponseEntity.ok("");
    }
}
