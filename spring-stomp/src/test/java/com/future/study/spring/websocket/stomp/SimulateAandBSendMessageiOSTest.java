package com.future.study.spring.websocket.stomp;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Random;

/**
 * 模拟A（Spring WebsocketStompClient）、B（iOS WebsocketStompKit）相互发送消息
 */
public class SimulateAandBSendMessageiOSTest {
    private final static Logger logger=LoggerFactory.getLogger(SimulateAandBSendMessageiOSTest.class);

    /**
     *
     * @throws InterruptedException
     */
    @Test
    public void testSendMessage() throws InterruptedException {
        String fromUsername = "A";
        String toUsername = "B";
        this.getStompClient(fromUsername, new StompSessionHandlerAdapter(){
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/user/queue/receiveMessage", new StompSessionHandlerAdapter() {
                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        logger.info(payload.toString());

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // Ignore
                        }

                        byte randomBytes[] = new byte[200*1024];
                        Random random = new Random();
                        random.nextBytes(randomBytes);
                        String request = createSendMessage(toUsername, "随机数据：" + Base64.encodeBase64String(randomBytes));
                        session.send("/app/sendMessage", request);
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Ignore
                }

                String request = createSendMessage(toUsername, "你好吗？用户B");
                session.send("/app/sendMessage", request);
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                exception.printStackTrace();
            }
        });
        System.out.println("Breakpoint holder");
    }

    /**
     * @param toUsername
     * @param message
     * @return
     */
    private String createSendMessage(String toUsername, String message){
        JSONObject request=new JSONObject();
        try {
            request.put("toUsername", toUsername);
            request.put("message", message);
        } catch (JSONException e) {
            // Ignore
        }
        return request.toString();
    }

    /**
     *
     * @param username
     * @param stompSessionHandler
     * @return
     */
    public WebSocketStompClient getStompClient(String username, StompSessionHandler stompSessionHandler){
//        List<Transport> transports = new ArrayList<>();
//        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketClient websocketClient=new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(websocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        String url = "ws://localhost:8080/chatEndpointWithoutSockJS";
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("username", username);
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        stompClient.connect(url,handshakeHeaders, stompHeaders, stompSessionHandler);
        return stompClient;
    }
}
