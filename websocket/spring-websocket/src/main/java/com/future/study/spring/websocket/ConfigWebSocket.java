package com.future.study.spring.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

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
        registry.addHandler(createWebSocketHandler(), "/websocketEndpoint").addInterceptors(websocketInterceptor());
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

    @Bean
    WebsocketInterceptor websocketInterceptor() {
        WebsocketInterceptor interceptor = new WebsocketInterceptor();
        return interceptor;
    }
}