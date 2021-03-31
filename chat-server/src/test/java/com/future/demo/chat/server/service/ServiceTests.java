package com.future.demo.chat.server.service;

import com.future.demo.chat.server.Application;
import com.future.demo.chat.server.common.PageResponse;
import com.future.demo.chat.server.exception.BusinessException;
import com.future.demo.chat.server.model.ChatMessageModel;
import com.future.demo.chat.server.value.object.SendMessageResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class ServiceTests {
    private final static Random RANDOM = new Random();

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MessageService messageService;

    @Test
    public void test() throws BusinessException {
        // 清空redis数据
        this.deleteByPattern("message#*");
        this.deleteByPattern("unreceived#*");

        String usernameSelf = "A用户";
        String usernameTo = "B用户";

        String idString = (String)redisTemplate.opsForValue().get(MessageService.KeyMessageId);
        long idTemporary = 0;
        if(!StringUtils.isEmpty(idString)) {
            idTemporary = Long.parseLong(idString);
        }

        // 发送消息
        int types[] = {1, 2};
        Map<Long, ChatMessageModel> mapMessageSend = new HashMap<>();
        for(int j=0; j<types.length; j++) {
            int type = types[j];
            int randomSize = RANDOM.nextInt(101);
            for (int i = 0; i < randomSize; i++) {
                String content = null;
                if(type==1) {
                    content = UUID.randomUUID().toString();
                } else {
                    byte []datas = new byte[1024*100];
                    RANDOM.nextBytes(datas);
                    content = new String(datas);
                }
                SendMessageResultVO sendMessageResultVO = this.messageService.send(usernameSelf, usernameTo, type, content);
                idTemporary++;
                long messageId = sendMessageResultVO.getMessageId();
                Assert.assertEquals(idTemporary, messageId);

                ChatMessageModel modelTemporary = new ChatMessageModel();
                modelTemporary.setId(messageId);
                modelTemporary.setUsernameFrom(usernameSelf);
                modelTemporary.setUsernameTo(usernameTo);
                modelTemporary.setType(type);
                modelTemporary.setContent(content);
                modelTemporary.setCreateTime(sendMessageResultVO.getCreateTime());
                mapMessageSend.put(messageId, modelTemporary);
            }
        }

        // 拉取消息
        Map<Long, ChatMessageModel> mapMessagePulled = new HashMap<>();
        PageResponse<ChatMessageModel> response = this.messageService.pull(usernameTo);
        Assert.assertTrue(response.getErrorCode()<=0);
        while(response.getTotalPage()>0) {
            List<ChatMessageModel> modelList = response.getData();
            List<Long> messageIdsToConfirm = new ArrayList<>();
            for(ChatMessageModel model : modelList) {
                long idTemporary1 = model.getId();
                messageIdsToConfirm.add(idTemporary1);
                mapMessagePulled.put(idTemporary1, model);
            }

            this.messageService.confirm(usernameTo, messageIdsToConfirm);
            response = this.messageService.pull(usernameTo);
            Assert.assertTrue(response.getErrorCode()<=0);
        }

        Assert.assertEquals(mapMessageSend.size(), mapMessagePulled.size());
        List<ChatMessageModel> messageSendList = new ArrayList<>(mapMessageSend.values());
        List<ChatMessageModel> messagePullList = new ArrayList<>(mapMessagePulled.values());
        for(int i=0 ; i<messageSendList.size(); i++) {
            ChatMessageModel modelMessageSend = messageSendList.get(i);
            ChatMessageModel model = messagePullList.get(i);
            Assert.assertEquals(model.getId(), modelMessageSend.getId());
            Assert.assertEquals(model.getUsernameFrom(), modelMessageSend.getUsernameFrom());
            Assert.assertEquals(model.getUsernameTo(), modelMessageSend.getUsernameTo());
            Assert.assertEquals(model.getType(), modelMessageSend.getType());
            Assert.assertEquals(model.getContent(), modelMessageSend.getContent());
            int compareResult = DateUtils.truncatedCompareTo(model.getCreateTime(), modelMessageSend.getCreateTime(), Calendar.SECOND);
            Assert.assertEquals(0, compareResult);
        }

        Set<String> keysTemporary = this.redisTemplate.keys("message#*");
        Assert.assertTrue(keysTemporary==null || keysTemporary.size()<=0);
        keysTemporary = this.redisTemplate.keys("unreceived#*");
        Assert.assertTrue(keysTemporary==null || keysTemporary.size()<=0);
    }

    void deleteByPattern(String pattern) {
        Set<String> keysTemporary = this.redisTemplate.keys(pattern);
        if(keysTemporary!=null && keysTemporary.size()>0) {
            this.redisTemplate.delete(keysTemporary);
        }
    }
}
