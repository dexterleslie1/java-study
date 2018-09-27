package com.future.study.http.library.testing.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

/**
 * @author Dexterleslie.Chan
 */
@ControllerAdvice
public class GExceptionHandler {
    @ExceptionHandler(value={NoHandlerFoundException.class,Exception.class})
    public ResponseEntity<Map<String,Object>> handle(Exception ex){
        ex.printStackTrace();
        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.getMessage());
        return responseEntity;
    }
}
