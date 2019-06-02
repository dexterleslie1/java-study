package com.future.study.spring.websocket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import java.security.Principal;

/**
 *
 */
@Configuration
@EnableWebSocket
public class ConfigWebSocket implements WebSocketConfigurer {
    private final static Logger logger = Logger.getLogger(ConfigWebSocket.class);

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