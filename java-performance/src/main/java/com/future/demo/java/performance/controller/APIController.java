package com.future.demo.java.performance.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
@RequestMapping(value="/api")
public class APIController {
    private final static Map<Integer, byte[]> memoryHolderMap = new HashMap<>();
    private final static Random Random = new Random();

    private int counter = 0;

    /**
     *
     * @return
     */
    @GetMapping(value="memory/consume", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> memoryConsume(){
        counter++;
        byte []bytes = new byte[1024*1024*100];
        Random.nextBytes(bytes);
        memoryHolderMap.put(counter, bytes);
        return ResponseEntity.ok("成功把 " + bytes.length/(1024*1024) + "mb 数据放到内存，map key：" + counter);
    }
}
