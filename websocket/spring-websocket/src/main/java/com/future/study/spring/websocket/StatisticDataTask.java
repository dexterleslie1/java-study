package com.future.study.spring.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Slf4j
@EnableScheduling
@Service
public class StatisticDataTask {
    @Autowired
    WebSocketRequestHandler webSocketRequestHandler;

    //每5秒执行1次
    @Scheduled(cron = "0/5 * * * * ?")
    public void count(){
        String message = "当前连接数：" + webSocketRequestHandler.getSessionList().size();
        List<WebSocketSession> sessionList = webSocketRequestHandler.getSessionList();
        if(sessionList!=null && sessionList.size()>0) {
            String ids = "";
            for(WebSocketSession session : sessionList) {
                if(!session.isOpen()) {
                    String id = session.getId();
                    ids = ids + id + ",";
                } else {
                    try {
                        session.sendMessage(new PingMessage());
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
            if(!StringUtils.isEmpty(ids)) {
                message = message + "，离线session列表：" + ids;
            }
        }
        log.error(message);
    }
}
