package com.future.study.spring.websocket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 */
public class WebSocketRequestHandler extends TextWebSocketHandler {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketRequestHandler.class);

    private final static String KeyAction = "action";

    private Map<String, WebSocketSession> mapSessions = new HashMap<>();
//    private Map<String, Runnable> mapRunnables = new HashMap<>();
    private List<WebSocketSession> sessionList = new ArrayList<>();

//    private ScheduledExecutorService scheduledExecutorService = null;

    @PostConstruct
    public void init() {
//        if(this.scheduledExecutorService==null) {
//            this.scheduledExecutorService = Executors.newScheduledThreadPool(2);
//        }
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
//        if(this.scheduledExecutorService!=null) {
//            this.scheduledExecutorService.shutdown();
//            this.scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS);
//        }
    }

    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String username = (String)session.getAttributes().get("username");
        if(StringUtils.isEmpty(username)) {
            CloseStatus closeStatus = CloseStatus.NORMAL.withReason("没有提供username");
            session.close(closeStatus);
        }

        sessionList.add(session);

        if (!StringUtils.isEmpty(username)) {
            // 关闭之前socket
            if(mapSessions.containsKey(username)) {
                mapSessions.get(username).close();
            }
//            if(mapRunnables.containsKey(username)) {
//                Runnable runnable = mapRunnables.get(username);
//                ((ScheduledThreadPoolExecutor) this.scheduledExecutorService).remove(runnable);
//            }

            mapSessions.put(username, session);
//            Runnable runnable = new HeartbeatRunnabe(session);
//            this.scheduledExecutorService.scheduleAtFixedRate(runnable, 5, 5, TimeUnit.SECONDS);
//            mapRunnables.put(username, runnable);

            logger.info("用户" + username + " 连接websocket服务器");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("收到来自客户消息：" + payload);
        if(!StringUtils.isEmpty(payload)){
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> data = null;
            try {
                data = objectMapper.readValue(payload, Map.class);
            }catch(JsonParseException ex){
                //
            }
            if(data != null && data.containsKey(KeyAction)){
                String action = data.get(KeyAction);
                if("MESSAGE".equals(action)){
                    String toUser = data.get("toUser");
                    if(!StringUtils.isEmpty(toUser)){
                        WebSocketSession toSession = mapSessions.get(toUser);
                        String content = data.get("content");
                        String fromUser = (String)session.getAttributes().get("username");
                        if(toSession != null) {
                            Map<String, String> messageMap = new HashMap<>();
                            messageMap.put("fromUser", fromUser);
                            messageMap.put("content", content);
                            toSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(messageMap)));
                            logger.info("用户" + fromUser +" 给用户" + toUser + " 发送消息 " + content);
                        }else {
                            logger.info("用户" + fromUser + " 尝试给用户" + toUser + " 发送消息，但不在线");
                        }

                    }
                }
            }
        }

        super.handleTextMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error(exception.getMessage(), exception);
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        sessionList.remove(session);

        String username = (String)session.getAttributes().get("username");
        if(!StringUtils.isEmpty(username)) {
            mapSessions.remove(username);

//            Runnable runnable = this.mapRunnables.get(username);
//            if(runnable!=null) {
//                ((ScheduledThreadPoolExecutor) this.scheduledExecutorService).remove(runnable);
//            }
        }
    }

    /**
     *
     * @return
     */
    public List<WebSocketSession> getSessionList() {
        return this.sessionList;
    }
}
