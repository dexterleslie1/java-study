package com.future.study.http.library.testing.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/http/library/api")
public class ApiController {
    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="getJSONResponse")
    public ResponseEntity<Map<String,Object>> getJSONResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "name",defaultValue = "") String name){
        if(StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("请指定名字");
        }
        String responsseString="你好，"+name;
        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responsseString);
        return responseEntity;
    }
}
