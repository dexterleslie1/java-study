package com.future.study.http.send.large.json;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/demo")
public class DemoController {
    @PostMapping(value="upload1")
    public ResponseEntity<Map<String,Object>> upload1(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "data",defaultValue = "") String data){
        int length=data.length()/1024/1024;
        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("success");
        return responseEntity;
    }
}
