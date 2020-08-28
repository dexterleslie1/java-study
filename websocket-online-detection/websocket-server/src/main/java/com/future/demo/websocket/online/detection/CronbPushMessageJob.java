package com.future.demo.websocket.online.detection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author dexterleslie@gmail.com
 */
@Component
public class CronbPushMessageJob {
    @Autowired
    SocketSessionService socketSessionService;

    /**
     *
     */
    @Scheduled(cron = "*/3 * * * * ?")
    public void process() {
        this.socketSessionService.doCronbPushMessage();
    }
}

