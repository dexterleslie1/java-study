//package com.future.study.spring.websocket;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.socket.PingMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.io.IOException;
//
//public class HeartbeatRunnabe implements Runnable {
//    private final static Logger LOGGER = LoggerFactory.getLogger(HeartbeatRunnabe.class);
//
//    private WebSocketSession session;
//
//    public HeartbeatRunnabe(WebSocketSession session) {
//        this.session = session;
//    }
//
//    @Override
//    public void run() {
////        if(this.session!=null && this.session.isOpen()) {
////            try {
////                this.session.sendMessage(new PingMessage());
////            } catch (IOException e) {
////                LOGGER.error(e.getMessage(), e);
////            }
////        }
//    }
//}
