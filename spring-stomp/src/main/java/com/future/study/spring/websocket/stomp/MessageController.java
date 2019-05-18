package com.future.study.spring.websocket.stomp;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

/**
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket-stomp-message-flow
 * @author Dexterleslie.Chan
 */
@RestController
public class MessageController {
    private final static Logger logger = Logger.getLogger(MessageController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate = null;

    /**
     * 模拟系统广播消息
     * @param greeting
     * @return
     */
    @RequestMapping(path = "/greetings")
    public String greetings(String greeting){
        Date timeNow = new Date();
        String message = timeNow + ": Hello," + greeting;
        this.simpMessagingTemplate.convertAndSend("/topic/broadcast/greetings", message);
        String responseMessage = "广播消息已发出";
        logger.debug(responseMessage);
        return responseMessage;
    }

    /**
     * 模拟用户A、B互相发送消息
     * @param request
     */
    @MessageMapping(value = "/sendMessage")
    public void sendMessage(@Payload String request, Principal principal){
        JSONObject requestObject = new JSONObject(request);
        String fromUsername = principal.getName();

        String toUsername = requestObject.getString("toUsername");
        String message = requestObject.getString("message");

        Date timeNow = new Date();
        message = timeNow + " " + fromUsername + ": " + message;
        this.simpMessagingTemplate.convertAndSendToUser(toUsername, "/queue/receiveMessage", message);
        logger.debug("消息已发送给用户" + toUsername);
    }
}
