package com.future.demo.chat.server;

import com.future.demo.chat.server.websocket.WebSocketRequestHandler;
import com.future.demo.chat.server.websocket.WebsocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class ConfigWebSocket implements WebSocketConfigurer {
    /**
     *
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(createWebSocketHandler(), "/chat").addInterceptors(websocketInterceptor());
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