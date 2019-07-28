package com.future.study.spring.websocket.cluster.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @author Dexterleslie.Chan
 */
@Configuration
@EnableWebSocket
public class ConfigWebsocket implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(websocketHandler(), "/websocketEndpoint");
    }

    @Bean
    public DemoWebsocketHandler websocketHandler(){
        DemoWebsocketHandler websocketHandler = new DemoWebsocketHandler();
        return websocketHandler;
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(1024*5);
        container.setMaxBinaryMessageBufferSize(1024*5);
        return container;
    }
}
