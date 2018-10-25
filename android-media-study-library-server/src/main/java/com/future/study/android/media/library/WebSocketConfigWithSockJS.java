package com.future.study.android.media.library;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;

/**
 * @author Dexterleslie.Chan
 */
//@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigWithSockJS implements WebSocketMessageBrokerConfigurer {

    /**
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/portfolio").withSockJS();
    }

    /**
     *
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic","/queue");
    }

    /**
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String userId=accessor.getFirstNativeHeader("userId");
                    if((Utils.userId1!=null&&Utils.userId1.equals(userId))||
                    		(Utils.userId2!=null&&Utils.userId2.equals(userId))){
                    	
                    }else{
	                    if(Utils.userId1==null){
	                    	Utils.userId1=userId;
	                    }else if(Utils.userId2==null){
	                    	Utils.userId2=userId;
	                    }
                    }
                    Principal user = new PrincipalObject(userId);
                    accessor.setUser(user);
                }
                return message;
            }
        });
    }

    /**
     *
     */
    class PrincipalObject implements Principal{
        private String userId;

        /**
         *
         * @param userId
         */
        PrincipalObject(String userId){
            this.userId=userId;
        }

        /**
         *
         * @return
         */
        @Override
        public String getName() {
           return this.userId;
        }
    }
}