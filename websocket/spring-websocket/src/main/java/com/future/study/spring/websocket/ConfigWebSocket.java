package com.future.study.spring.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 *
 */
@Configuration
@EnableWebSocket
public class ConfigWebSocket implements WebSocketConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(ConfigWebSocket.class);

    /**
     *
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(createWebSocketHandler(), "/websocketEndpoint");
    }

    /**
     *
     * @return
     */
    @Bean
    public WebSocketHandler createWebSocketHandler(){
        WebSocketHandler webSocketHandler = new WebSocketRequestHandler();
        return webSocketHandler;
    }
}