package com.future.study.spring.websocket.cluster.rabbitmq;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Dexterleslie.Chan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={ApplicationSpringWSClusterRMFanout.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ApplicationSpringWSClusterRMFanoutTests {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationSpringWSClusterRMFanoutTests.class);

    @Autowired
    private MQHandler mqHandler = null;

    @Test
    public void test1() throws ExecutionException, InterruptedException, IOException, TimeoutException {
        WebSocketClient client = new StandardWebSocketClient();
        String url = "ws://localhost:8080/websocketEndpoint";

        int total = 20;
        List<WebSocketSession> sessionList = new ArrayList<>();
        for(int i=0; i<total; i++) {
            ListenableFuture<WebSocketSession> future = client.doHandshake(new TextWebSocketHandler() {
                @Override
                public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                    String userId = UUID.randomUUID().toString();
                    ObjectNode request = JsonNodeFactory.instance.objectNode();
                    request.put("action", "connect");
                    request.put("userId", userId);
                    String stringTemporary = request.toString();
                    session.getAttributes().put("userId", userId);
                    session.sendMessage(new TextMessage(stringTemporary));
                }
            }, url);

            WebSocketSession socketSession = future.get();
            sessionList.add(socketSession);
        }

        // 等待websocket连接到服务器
        Thread.sleep(1000);

        int countDown = 100;
        this.mqHandler.setCountDown(countDown);

        for(int i=0; i<countDown; i++) {
            WebSocketSession[] sessionsTemporary = randomPickupWebSocketSession(sessionList);
            WebSocketSession sessionFrom = sessionsTemporary[0];
            WebSocketSession sessionTo = sessionsTemporary[1];
            String toUserId = (String) sessionTo.getAttributes().get("userId");
            ObjectNode request = JsonNodeFactory.instance.objectNode();
            request.put("action", "send");
            request.put("toUserId", toUserId);
            sessionFrom.sendMessage(new TextMessage(request.toString()));
        }

        if(!this.mqHandler.getCountDownLatch().await(5, TimeUnit.SECONDS)) {
            throw new TimeoutException();
        }

        for(int i=0; i<sessionList.size(); i++){
            WebSocketSession socketSession = sessionList.get(i);
            socketSession.close();
        }
    }

    private final static Random random = new Random();
    private WebSocketSession[] randomPickupWebSocketSession(List<WebSocketSession> sessions){
        int size = sessions.size();
        int randomIndex1 = random.nextInt(size);
        int randomIndex2 = random.nextInt(size);
        while(randomIndex1 == randomIndex2){
            randomIndex2 = random.nextInt(size);
        }
        WebSocketSession []sessionsTemporary = new WebSocketSession[]{
                sessions.get(randomIndex1),sessions.get(randomIndex2)
        };
        return sessionsTemporary;
    }
}
