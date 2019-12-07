package com.future.demo.java.spring.cloud.stream.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
@RequestMapping("/api/stream")
public class APIController {
    @Autowired
    private ProducerService producerService = null;

    /**
     *
     * @return
     */
    @GetMapping(path = "produceDestination1", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> produceDestination1() {
        this.producerService.produceDestination1();
        return ResponseEntity.ok("成功发布消息");
    }

    /**
     *
     * @return
     */
    @GetMapping(path = "produceDestination2", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> produceDestination2() {
        this.producerService.produceDestination2();
        return ResponseEntity.ok("成功发布消息");
    }

    /**
     *
     * @return
     */
    @GetMapping(path = "produceDelay", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> produceDelay() {
        this.producerService.produceDelay();
        return ResponseEntity.ok("成功发布消息");
    }
}
