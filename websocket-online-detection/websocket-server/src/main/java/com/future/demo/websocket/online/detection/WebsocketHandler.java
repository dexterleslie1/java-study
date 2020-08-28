package com.future.demo.websocket.online.detection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

/**
 *
 * @author dexterleslie@gmail.com
 */
public class WebsocketHandler extends TextWebSocketHandler {
    private final static Logger logger = LoggerFactory.getLogger(WebsocketHandler.class);

    @Autowired
    SocketSessionService socketSessionService;

    private final static String KeyAction = "action";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        this.socketSessionService.addConnected(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String payload = message.getPayload();
            if (!StringUtils.isEmpty(payload)) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> data = null;
                try {
                    data = objectMapper.readValue(payload, Map.class);
                } catch (JsonParseException ex) {
                    //
                }
                if (data != null && data.containsKey(KeyAction)) {
                    String action = data.get(KeyAction);

                    if (!"CONNECT".equals(action)
                            && !this.socketSessionService.isLogin(session)) {
                        throw new BusinessException("您未登录");
                    }

                    if ("CONNECT".equals(action)) {
                        this.socketSessionService.addOnline(session);
                    } else if ("KEEPALIVE".equals(action)) {
                        String sessionId = session.getId();
                        this.socketSessionService.keepOnline(sessionId);
                    }
                } else {
                    throw new BusinessException("非法请求");
                }
            }
        } catch (BusinessException ex) {
            session.sendMessage(new TextMessage(ex.getMessage()));
        } finally {
            super.handleTextMessage(session, message);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        logger.error(exception.getMessage(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        String sessionId = session.getId();
        this.socketSessionService.removeConnected(sessionId, true);
        this.socketSessionService.removeOnline(sessionId);
    }
}
