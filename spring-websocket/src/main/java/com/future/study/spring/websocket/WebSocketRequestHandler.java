package com.future.study.spring.websocket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class WebSocketRequestHandler extends TextWebSocketHandler {
    private final static Logger logger = Logger.getLogger(WebSocketRequestHandler.class);

    private final static String KeyAction = "action";

    private Map<String, WebSocketSession> mapSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("invoked afterConnectionEstablished");
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
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
                if("CONNECT".equals(action)){
                    String username = data.get("username");
                    if(!StringUtils.isEmpty(username)){
                        session.getAttributes().put("username", username);
                        mapSessions.put(username, session);
                        logger.debug("用户" + username + " 连接websocket服务器");
                    }
                }else if("MESSAGE".equals(action)){
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
                            logger.debug("用户" + fromUser +" 给用户" + toUser + " 发送消息 " + content);
                        }else {
                            logger.debug("用户" + fromUser + " 尝试给用户" + toUser + " 发送消息，但不在线");
                        }

                    }
                }
            }
        }

        super.handleTextMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.debug("invoked handleTransportError");
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.debug("invoked afterConnectionClosed");
        super.afterConnectionClosed(session, status);
    }
}
