package com.future.study.spring.websocket.stomp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 模拟广播消息
 */
public class SimulateBroadcastMessageTest {
    private final static Logger logger=LoggerFactory.getLogger(SimulateBroadcastMessageTest.class);

    /**
     * 演示广播
     * @throws InterruptedException
     */
    @Test
    public void testBroadcast() throws InterruptedException {
        // 接收到3次服务器发送的广播消息后测试程序退出
        CountDownLatch countDownLatch = new CountDownLatch(3);
        this.getStompClient(new StompSessionHandlerAdapter(){
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/topic/broadcast/greetings", new StompSessionHandlerAdapter() {
                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        logger.info("收到服务器广播消息：" + payload);
                        countDownLatch.countDown();
                    }
                });
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                exception.printStackTrace();
                countDownLatch.countDown();
            }
        });
        while(!countDownLatch.await(1, TimeUnit.SECONDS));
    }

    /**
     *
     * @param stompSessionHandler
     * @return
     */
    public WebSocketStompClient getStompClient(StompSessionHandler stompSessionHandler){
//        List<Transport> transports = new ArrayList<>();
//        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketClient websocketClient=new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(websocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        String url = "ws://localhost:8080/chatEndpointWithoutSockJS";
        stompClient.connect(url,stompSessionHandler);
        return stompClient;
    }
}
