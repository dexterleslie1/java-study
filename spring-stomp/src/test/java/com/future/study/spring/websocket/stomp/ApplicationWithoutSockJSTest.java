package com.future.study.spring.websocket.stomp;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dexterleslie
 * @date 2018年08月06日
 * @time 12:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={ApplicationWithoutSockJS.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationWithoutSockJSTest {
    private final static Logger logger=LoggerFactory.getLogger(ApplicationWithoutSockJSTest.class);

    /**
     * 测试用户a发送消息给用户b
     */
    @Test
    public void test_usera_send_message_to_userb() throws InterruptedException {
        WebSocketStompClient stompClient2=this.getStompClient("1-2",new StompSessionHandlerAdapter(){
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/queue/broadcast", broadcastHandler);
                session.subscribe("/user/queue/receiveMessage", new StompSessionHandlerAdapter() {
                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        logger.info("用户a："+payload+"");

                        JSONObject request=new JSONObject();
                        try {
                            request.put("userId","1-1");
                            request.put("content","当然可以");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        session.send("/app/sendTo", request.toString());
                    }
                });
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                exception.printStackTrace();
            }
        });

        Thread.sleep(1000);

        WebSocketStompClient stompClient1=this.getStompClient("1-1",new StompSessionHandlerAdapter(){
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/queue/broadcast", broadcastHandler);
                session.subscribe("/user/queue/receiveMessage", new StompSessionHandlerAdapter() {
                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        logger.info("用户b："+payload+"");
                    }
                });

                session.send("/app/broadcast", null);
                JSONObject request=new JSONObject();
                try {
                    request.put("userId","1-2");
                    request.put("content","可以交个朋友吗？");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                session.send("/app/sendTo", request.toString());
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                exception.printStackTrace();
            }
        });

        Thread.sleep(1000);
    }

    /**
     *
     */
    private StompSessionHandler broadcastHandler=new StompSessionHandlerAdapter() {
        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            logger.info("用户"+headers.get("userId")+"收到服务器广播消息["+payload+"]");
        }
    };

    /**
     *
     * @param userId
     * @param stompSessionHandler
     * @return
     */
    public WebSocketStompClient getStompClient(String userId,StompSessionHandler stompSessionHandler){
//        List<Transport> transports = new ArrayList<>();
//        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketClient websocketClient=new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(websocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        String url = "ws://localhost:8080/portfolio";
        StompHeaders headers = new StompHeaders();
        headers.add("userId", userId);
        stompClient.connect(url, new WebSocketHttpHeaders(),headers,stompSessionHandler);
        return stompClient;
    }
}
