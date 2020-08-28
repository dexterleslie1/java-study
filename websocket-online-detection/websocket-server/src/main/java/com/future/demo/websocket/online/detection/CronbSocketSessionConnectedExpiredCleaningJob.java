package com.future.demo.websocket.online.detection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author dexterleslie@gmail.com
 */
@Component
public class CronbSocketSessionConnectedExpiredCleaningJob {
    @Autowired
    SocketSessionService socketSessionService;

    /**
     *
     */
    @Scheduled(cron = "*/1 * * * * ?")
    public void process() {
        this.socketSessionService.doCronbRemoveConnectedExpired();
    }
}

