package com.future.study.http.send.large.json;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandler1{
    @ExceptionHandler(value={NoHandlerFoundException.class,Exception.class})
    public ResponseEntity<Map<String,Object>> handle(Exception ex){
        ex.printStackTrace();
        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.getMessage());
        return responseEntity;
    }
}
