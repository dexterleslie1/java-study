package com.future.demo.chat.server.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.future.demo.chat.server.common.BaseResponse;
import com.future.demo.chat.server.common.ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Slf4j
public class WebSocketRequestHandler extends TextWebSocketHandler {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Map<String, WebSocketSession> mapSessions = new HashMap<>();
    private List<WebSocketSession> sessionList = new ArrayList<>();

    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String username = (String)session.getAttributes().get("usernameSelf");
        if(StringUtils.isEmpty(username)) {
            CloseStatus closeStatus = CloseStatus.NORMAL.withReason("没有提供usernameSelf参数");
            session.close(closeStatus);
            String clientIp = (String)session.getAttributes().get("clientIp");
            String warnMessage = "客户端ip[" + clientIp + "]没有提供usernameSelf参数";
            log.warn(warnMessage);
            return;
        }

        sessionList.add(session);

        if (!StringUtils.isEmpty(username)) {
            // 关闭之前socket
            if(mapSessions.containsKey(username)) {
                mapSessions.get(username).close();
            }

            mapSessions.put(username, session);

            ObjectResponse<ObjectNode> response = new ObjectResponse<>();
            ObjectNode node = OBJECT_MAPPER.createObjectNode();
            node.put("action", "login");
            node.put("message", "成功连接socket服务器");
            response.setData(node);
            sendMessage(session, response);

            log.debug("用户" + username + " 连接websocket服务器");
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error(exception.getMessage(), exception);
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        sessionList.remove(session);

        String username = (String)session.getAttributes().get("username");
        if(!StringUtils.isEmpty(username)) {
            mapSessions.remove(username);
        }
    }

    public boolean notifyPull(String usernameTo) {
        Map<String, Object> data = new HashMap<>();
        data.put("action", "pull");
        ObjectResponse<Map<String, Object>> response = new ObjectResponse<>();
        response.setData(data);
        return this.sendMessage(usernameTo, response);
    }

    public boolean sendMessage(String usernameTo, BaseResponse response) {
        if(StringUtils.isEmpty(usernameTo) || response==null) {
            return false;
        }

        if(this.mapSessions.containsKey(usernameTo)) {
            WebSocketSession session = this.mapSessions.get(usernameTo);
            return sendMessage(session, response);
        }
        return false;
    }

    public boolean sendMessage(WebSocketSession session, BaseResponse response) {
        if(session==null || response==null) {
            return false;
        }

        if(session.isOpen()) {
            try {
                String json = OBJECT_MAPPER.writeValueAsString(response);
                session.sendMessage(new TextMessage(json));
                return true;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return false;
    }
}