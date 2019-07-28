package com.future.study.spring.websocket.cluster.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author Dexterleslie.Chan
 */
public class MQHandler {
    private final static Logger logger = LoggerFactory.getLogger(MQHandler.class);

    private Map<String, WebSocketSession> sessionsRegistry = new HashMap<>();
    private CountDownLatch countDownLatch = null;

    public Map<String, WebSocketSession> getSessionsRegistry() {
        return this.sessionsRegistry;
    }

    public void setCountDown(int countDown) {
        this.countDownLatch = new CountDownLatch(countDown);
    }

    public CountDownLatch getCountDownLatch() {
        return this.countDownLatch;
    }

    public void handle(String payload) {
        if(this.countDownLatch != null){
            this.countDownLatch.countDown();
        }
    }
}
