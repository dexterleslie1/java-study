package com.future.demo.chat.server.service;

import com.future.demo.chat.server.websocket.WebSocketRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DelayedService {
    @Autowired
    WebSocketRequestHandler webSocketRequestHandler;

    ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void init1() {
        if(scheduledExecutorService==null) {
            scheduledExecutorService = Executors.newScheduledThreadPool(2);
        }
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        if(scheduledExecutorService!=null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    public void notifyPullDelay(String usernameTo) {
        if(scheduledExecutorService!=null) {
            scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    if(webSocketRequestHandler!=null) {
                        webSocketRequestHandler.notifyPull(usernameTo);
                    }
                }
            }, 3, TimeUnit.SECONDS);
        }
    }
}
