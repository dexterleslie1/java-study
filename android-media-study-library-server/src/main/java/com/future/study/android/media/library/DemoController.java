package com.future.study.android.media.library;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author Dexterleslie.Chan
 */
@Controller
public class DemoController {
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public DemoController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate=simpMessagingTemplate;
    }

    /**
     * 广播消息
     * @return
     */
    @MessageMapping("/broadcast")
    @SendTo("/queue/broadcast")
    public String broadcast(){
        String message="大家好";
        return message;
    }

    /**
     * 发送消息给指定用户
     * @param requestString
     * @throws JSONException 
     */
    @MessageMapping("/sendTo")
    public void sendTo(String requestString) throws JSONException {
        JSONObject requestObject=new JSONObject(requestString);
        String userId=requestObject.getString("userId");
        String content=requestObject.getString("content");
        // 发送消息给指定用户，destination参数不能写成 /user/queue/receiveMessage
        // 否则不能发送消息给指定用户
        this.simpMessagingTemplate.convertAndSendToUser(
                userId,
                "/queue/receiveMessage",
                content);
    }
}
