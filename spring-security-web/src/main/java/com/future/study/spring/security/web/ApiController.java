package com.future.study.spring.security.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/api/auth")
public class ApiController {

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="a1")
    public ResponseEntity<Map<String,Object>> a1(
            HttpServletRequest request,
            HttpServletResponse response){
        Map<String,Object> mapReturn=new HashMap<>();
        mapReturn.put("dataObject",""+new Date());
        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapReturn);
        return responseEntity;
    }
}
