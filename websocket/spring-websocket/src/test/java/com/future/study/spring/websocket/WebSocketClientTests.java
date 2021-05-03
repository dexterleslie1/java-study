package com.future.study.spring.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class WebSocketClientTests {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketClientTests.class);

    /**
     *
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void testConnect() throws ExecutionException, InterruptedException, IOException {
        String host = System.getenv("host");
        int port = Integer.parseInt(System.getenv("port"));

        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0; i<1000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        WebSocketClient client = new StandardWebSocketClient();
                        String url = "ws://" + host + ":" + port + "/websocketEndpoint";
                        ListenableFuture<WebSocketSession> future = client.doHandshake(new TextWebSocketHandler(), url);
                        WebSocketSession socketSession = future.get();

                        Map<String, String> requestMap = new HashMap<>();
                        requestMap.put("action", "CONNECT");
                        requestMap.put("username", "A");
                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = objectMapper.writeValueAsString(requestMap);
                        socketSession.sendMessage(new TextMessage(json));

                        //        for(int i=0; i<100; i++){
                        //            Random random = new Random();
                        //            byte []bytes = new byte[10];
                        //            random.nextBytes(bytes);
                        //            String content = Base64.getEncoder().encodeToString(bytes);
                        //
                        //            Map<String, String> messageMap = new HashMap<>();
                        //            messageMap.put("action", "MESSAGE");
                        //            messageMap.put("toUser", "B");
                        //            messageMap.put("content", "随机数据 " + content);
                        //            TextMessage message = new TextMessage(new ObjectMapper().writeValueAsString(messageMap));
                        //            socketSession.sendMessage(message);
                        //            Thread.sleep(1000);
                        //        }

//                    socketSession.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();
        while(!executorService.awaitTermination(10, TimeUnit.MILLISECONDS));

        System.out.println("jfkjdkfd");
    }
}
