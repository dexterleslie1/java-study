package com.future.demo.websocket.online.detection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

/**
 *
 * @author dexterleslie@gmail.com
 */
@Configuration
@EnableWebSocket
public class ConfigWebsocket implements WebSocketConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(ConfigWebsocket.class);

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
        WebSocketHandler webSocketHandler = new WebsocketHandler();
        return webSocketHandler;
    }
}