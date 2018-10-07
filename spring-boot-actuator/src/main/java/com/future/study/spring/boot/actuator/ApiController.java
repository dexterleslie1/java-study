package com.future.study.spring.boot.actuator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/api")
public class ApiController {
    private List<byte []> bytesHolder=new ArrayList<>();

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="consume5mbmemory")
    public ResponseEntity<Map<String,Object>> consume5mbmemory(
            HttpServletRequest request,
            HttpServletResponse response){
        bytesHolder.add(new byte[5*1024*1024]);
        return null;
    }
}
