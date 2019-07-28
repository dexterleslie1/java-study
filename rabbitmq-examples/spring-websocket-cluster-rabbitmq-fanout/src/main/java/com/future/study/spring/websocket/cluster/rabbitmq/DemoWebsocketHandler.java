package com.future.study.spring.websocket.cluster.rabbitmq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dexterleslie.Chan
 */
public class DemoWebsocketHandler extends TextWebSocketHandler {
    private final static Logger logger = LoggerFactory.getLogger(DemoWebsocketHandler.class);

    private final static ObjectMapper OMInstance = new ObjectMapper();

    private final static String QueuePrefix = "queue#demo#";

    @Autowired
    private AmqpTemplate amqpTemplate = null;
    @Autowired
    private MQHandler mqHandler = null;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        if(StringUtils.isEmpty(payload)) {
            return;
        }

        JsonNode node = OMInstance.readTree(payload);
        JsonNode actionNode = node.get("action");
        if(actionNode == null){
            return;
        }

        String action = actionNode.asText();
        if("connect".equalsIgnoreCase(action)) {
            JsonNode userIdNode = node.get("userId");
            if(userIdNode == null) {
                return;
            }

            String userId = userIdNode.asText();
            this.mqHandler.getSessionsRegistry().put(userId, session);
            session.getAttributes().put("userId", userId);
            logger.info("用户" + userId + " 登陆");
        }else if("send".equalsIgnoreCase(action)) {
            JsonNode nodeToUserId = node.get("toUserId");
            if(nodeToUserId == null) {
                return;
            }

            String toUserId = nodeToUserId.asText();

            ObjectNode messageObject = JsonNodeFactory.instance.objectNode();
            messageObject.put("fromUserId", (String)session.getAttributes().get("userId"));
            messageObject.put("toUserId", toUserId);
            messageObject.put("content", "Hello " + toUserId);

            this.amqpTemplate.convertAndSend(ConfigRabbitMQ.ExchangeName, "",  messageObject.toString());
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

        String userId = (String)session.getAttributes().get("userId");
        if(session.isOpen()) {
            session.close();
        }
        this.mqHandler.getSessionsRegistry().remove(userId);
        logger.info("用户" + userId + " 退出");
    }
}
