package com.future.study.spring.websocket;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 *
 */
public class WebSocketClientTests {
    private final static Logger logger = Logger.getLogger(WebSocketClientTests.class);

    @Test
    public void testConnect() throws ExecutionException, InterruptedException, IOException {
        WebSocketClient client = new StandardWebSocketClient();
        String url = "ws://localhost:8080/websocketEndpoint";
        ListenableFuture<WebSocketSession> future = client.doHandshake(new AbstractWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                super.afterConnectionEstablished(session);
            }
        }, url);

        WebSocketSession socketSession = future.get();
        socketSession.sendMessage(new TextMessage("{\"command\":\"test\",\"parameters\":{\"type\":\"conn\"}}"));
        socketSession.close();
//        Thread.sleep(1000);
    }
}
