package com.future.study.android.media.library;

import java.security.Principal;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author Dexterleslie.Chan
 */
@Controller
public class ApiController {
	private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ApiController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate=simpMessagingTemplate;
    }
	
    /**
     * Voice data transfer
     * @return
     * @throws JSONException 
     */
	@MessageMapping("/sendVoiceData")
    public void sendTo(String json,Principal principal) throws JSONException{
		String currentUserId=principal.getName();
		String toUserId=null;
		if(Utils.userId1!=null&&Utils.userId1.equals(currentUserId)){
			toUserId=Utils.userId2;
		}
		if(Utils.userId2!=null&&Utils.userId2.equals(currentUserId)){
			toUserId=Utils.userId1;
		}
//		toUserId=principal.getName();
		
		if(toUserId!=null){
			JSONObject object=new JSONObject(json);
			String voiceData=object.getString("voiceData");
			this.simpMessagingTemplate.convertAndSendToUser(
					toUserId,
	                "/queue/receiveVoiceData",
	                voiceData);
		}
    }
}
