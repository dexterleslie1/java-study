package com.future.demo.network.optimization.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

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
    public WebSocketRequestHandler createWebSocketHandler(){
        WebSocketRequestHandler webSocketHandler = new WebSocketRequestHandler();
        return webSocketHandler;
    }

    /**
     *
     * @return
     */
    @Bean
    SessionRegistry sessionRegistry() {
        SessionRegistry registry = new SessionRegistry();
        return registry;
    }
}