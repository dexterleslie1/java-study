package com.future.study.spring.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 *
 */
public class WebSocketClientTests {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketClientTests.class);

    @Test
    public void testConnect() throws ExecutionException, InterruptedException, IOException {
        WebSocketClient client = new StandardWebSocketClient();
        String url = "ws://localhost:8080/websocketEndpoint";
        ListenableFuture<WebSocketSession> future = client.doHandshake(new TextWebSocketHandler(){
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                logger.debug(message.toString());
            }
        }, url);

        WebSocketSession socketSession = future.get();

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("action", "CONNECT");
        requestMap.put("username", "A");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(requestMap);
        socketSession.sendMessage(new TextMessage(json));

        for(int i=0; i<100; i++){
            Random random = new Random();
            byte []bytes = new byte[10];
            random.nextBytes(bytes);
            String content = Base64.getEncoder().encodeToString(bytes);

            Map<String, String> messageMap = new HashMap<>();
            messageMap.put("action", "MESSAGE");
            messageMap.put("toUser", "B");
            messageMap.put("content", "随机数据 " + content);
            TextMessage message = new TextMessage(new ObjectMapper().writeValueAsString(messageMap));
            socketSession.sendMessage(message);
            Thread.sleep(1000);
        }

        socketSession.close();
    }
}
