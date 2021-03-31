package com.future.demo.chat.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
public abstract class SocketClient {
    private String username;
    private String apiHost;
    private WebSocketClient webSocketClient = new StandardWebSocketClient();
    private WebSocketSession session = null;

    public SocketClient(String username,
                        String apiHost){
        this.username = username;
        this.apiHost = apiHost;
    }

    public abstract void onMessage(String message) throws Exception;

    public WebSocketSession connect() throws ExecutionException, InterruptedException, IOException, TimeoutException {
        URI uri = URI.create("ws://" + apiHost + ":8080/chat");
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        if(!StringUtils.isEmpty(username)) {
            username = URLEncoder.encode(username, "utf-8");
        }
        headers.put("usernameSelf", Arrays.asList(username));
        ListenableFuture<WebSocketSession> future = webSocketClient.doHandshake(new TextWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                onMessage(message.getPayload());
            }
            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
                log.warn(status.toString());
            }
        }, headers, uri);

        session = future.get();
        return session;
    }

    public void close() throws IOException {
        if(session != null && session.isOpen()){
            session.close();
        }
        session = null;
    }
}
