package com.future.study.spring.websocket.cluster.rabbitmq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author Dexterleslie.Chan
 */
public class MQHandler {
    private final static Logger logger = LoggerFactory.getLogger(MQHandler.class);
    private final static ObjectMapper OMInstance = new ObjectMapper();

    private Map<String, WebSocketSession> sessionsRegistry = new ConcurrentHashMap<>();

    public Map<String, WebSocketSession> getSessionsRegistry() {
        return this.sessionsRegistry;
    }

    public void handle(String payload) {
        if(StringUtils.isEmpty(payload)) {
            logger.warn("Payload is empty");
            return;
        }

        JsonNode node = null;
        try {
            node = OMInstance.readTree(payload);
        } catch (IOException e) {
            //
        }

        if(node == null) {
            logger.warn("Payload is invalid " + payload);
            return;
        }

        JsonNode nodeContent = node.get("content");
        JsonNode nodeToUserId = node.get("toUserId");
        if(nodeToUserId == null
                || StringUtils.isEmpty(nodeToUserId.asText())){
            logger.warn("Payload doesn't contain toUserId");
            return;
        }
        String toUserId = nodeToUserId.asText();

        String content = null;
        if(nodeContent == null){
            content = "";
        }else {
            content = nodeContent.asText();
        }
        WebSocketSession session = sessionsRegistry.get(toUserId);
        if(session == null || !session.isOpen()) {
            return;
        }
        try {
            synchronized (session) {
                session.sendMessage(new TextMessage(content));
            }
        } catch (IOException e) {
            //
        }
    }
}
