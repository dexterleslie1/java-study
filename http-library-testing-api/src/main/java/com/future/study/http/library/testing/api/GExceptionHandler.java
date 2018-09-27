package com.future.study.http.library.testing.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dexterleslie.Chan
 */
@ControllerAdvice
public class GExceptionHandler {
    @ExceptionHandler(value={NoHandlerFoundException.class,Exception.class})
    public ResponseEntity<Map<String,Object>> handle(Exception ex){
        ex.printStackTrace();

        Map<String,Object> mapReturn=new HashMap<>();
        mapReturn.put("errorCode",50000);
        mapReturn.put("errorMessage",ex.getMessage());

        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapReturn);
        return responseEntity;
    }
}
