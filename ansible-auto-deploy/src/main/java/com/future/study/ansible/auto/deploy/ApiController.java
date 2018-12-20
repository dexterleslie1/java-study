package com.future.study.ansible.auto.deploy;

import org.springframework.http.MediaType;
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
@RequestMapping(value="/a")
public class ApiController {
    /**
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="a1")
    public ResponseEntity<Map<String,Object>> a1(
            HttpServletRequest request,
            HttpServletResponse response) throws InterruptedException {
        Date timeStart = new Date();
        Random random = new Random();
        int maxMilliseconds = 5;
        for(int i = 0; i < 1000; i++){
            byte [] datas = new byte[1024*100];
            random.nextBytes(datas);
            Base64.getEncoder().encodeToString(datas);
            int milliseconds = random.nextInt(maxMilliseconds);
            if(milliseconds <= 0){
                milliseconds = 1;
            }
            Thread.sleep(milliseconds);
        }
        Date timeEnd = new Date();
        long milliseconds1 = timeEnd.getTime() - timeStart.getTime();
        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(milliseconds1);
        return responseEntity;
    }
}
