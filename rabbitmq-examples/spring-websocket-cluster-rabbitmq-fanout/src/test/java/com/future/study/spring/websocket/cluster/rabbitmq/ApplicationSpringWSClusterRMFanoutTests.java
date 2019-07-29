package com.future.study.spring.websocket.cluster.rabbitmq;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author Dexterleslie.Chan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={ApplicationSpringWSClusterRMFanout.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ApplicationSpringWSClusterRMFanoutTests {
    @Test
    public void test1() throws ExecutionException, InterruptedException, IOException, TimeoutException {
        WebSocketClient client = new StandardWebSocketClient();
        String url = "ws://localhost:8080/websocketEndpoint";
//        String url1 = "ws://localhost:8081/websocketEndpoint";

        Map<String, Integer> sendCounter = new HashMap<>();
        Map<String, Integer> receiveCounter = new HashMap<>();

        int total = 100;
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

                @Override
                protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                    String userId = (String)session.getAttributes().get("userId");
                    synchronized (receiveCounter) {
                        if (!receiveCounter.containsKey(userId)) {
                            receiveCounter.put(userId, 0);
                        }
                        int counter = receiveCounter.get(userId);
                        receiveCounter.put(userId, counter+1);
                    }
                }
            }, /*(i%2==0?url:url1)*/url);

            WebSocketSession socketSession = future.get();
            sessionList.add(socketSession);
        }

        // 等待websocket连接到服务器
        Thread.sleep(3000);

        ExecutorService executorService = Executors.newCachedThreadPool();
        Random random = new Random();
        int concurrentCount = 10;
        for(int j=0; j<concurrentCount; j++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        WebSocketSession[] sessionsTemporary = randomPickupWebSocketSession(sessionList);
                        WebSocketSession sessionFrom = sessionsTemporary[0];
                        WebSocketSession sessionTo = sessionsTemporary[1];
                        String toUserId = (String) sessionTo.getAttributes().get("userId");
                        ObjectNode request = JsonNodeFactory.instance.objectNode();
                        request.put("action", "send");
                        request.put("toUserId", toUserId);
                        request.put("content", UUID.randomUUID().toString());

                        synchronized (sessionFrom) {
                            if (!sendCounter.containsKey(toUserId)) {
                                sendCounter.put(toUserId, 0);
                            }
                            int counter = sendCounter.get(toUserId);
                            sendCounter.put(toUserId, counter + 1);

                            try {
                                sessionFrom.sendMessage(new TextMessage(request.toString()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        int randomMilliseconds = random.nextInt(100);
                        if(randomMilliseconds == 0) {
                            randomMilliseconds = 10;
                        }
                        try {
                            Thread.sleep(randomMilliseconds);
                        } catch (InterruptedException e) {
                            //
                        }
                    }
                }
            });
        }
        executorService.shutdown();
        while(!executorService.awaitTermination(100, TimeUnit.MILLISECONDS));

        Thread.sleep(3000);

        for(int i=0; i<sessionList.size(); i++){
            WebSocketSession socketSession = sessionList.get(i);
            socketSession.close();
        }

        Thread.sleep(2000);

        Assert.assertEquals(sendCounter.size(), receiveCounter.size());
        for(String key : sendCounter.keySet()) {
            Assert.assertEquals(sendCounter.get(key), receiveCounter.get(key));
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
