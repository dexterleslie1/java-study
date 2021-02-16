package com.future.demo.network.optimization.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 */
public class WebSocketRequestHandler extends TextWebSocketHandler {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketRequestHandler.class);

    @Autowired
    SessionRegistry registry;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("invoked afterConnectionEstablished");
        super.afterConnectionEstablished(session);
        registry.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String payload = message.getPayload();
        logger.info("收到来自客户消息：" + payload);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error(exception.getMessage(), exception);
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("invoked afterConnectionClosed,status="+status);
        super.afterConnectionClosed(session, status);
        registry.remove(session);
    }
}
