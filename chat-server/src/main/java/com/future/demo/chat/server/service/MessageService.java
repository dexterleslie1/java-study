package com.future.demo.chat.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.demo.chat.server.common.PageResponse;
import com.future.demo.chat.server.exception.BusinessException;
import com.future.demo.chat.server.model.ChatMessageModel;
import com.future.demo.chat.server.value.object.SendMessageResultVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MessageService implements Serializable {
    private final static ObjectMapper OBJECTMAPPER = new ObjectMapper();
    private final static Integer SIZEPULLUNRECEIVEDBATCH = 5;
    private final static Integer SIZE500K = 500*1024;
    public final static String KeyMessageId = "MessageId";

    @Autowired
    IdService idService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    DelayedService delayedService;
    @Autowired
    CacheManager cacheManager;

    Cache cacheNotifyPullTimeout;

    @PostConstruct
    public void init1() {
        cacheNotifyPullTimeout = this.cacheManager.getCache("cacheNotifyPullTimeout");
    }

    /**
     *
     * @param usernameSelf
     * @param usernameTo
     * @param type 1、文字 2、图片
     * @param content
     * @return
     * @throws BusinessException
     */
    public SendMessageResultVO send(String usernameSelf,
                                    String usernameTo,
                                    int type,
                                    String content) throws BusinessException {
        if(StringUtils.isEmpty(usernameSelf)) {
            throw new BusinessException("没有提供usernameSelf");
        }
        if(StringUtils.isEmpty(usernameTo)) {
            throw new BusinessException("没有提供usernameTo");
        }
        if(type!=1 && type!=2) {
            throw new BusinessException("消息类型type值[" + type + "]，取值范围1、文字 2、图片");
        }
        if(StringUtils.isEmpty(content)) {
            throw new BusinessException("没有提供content");
        }

        // 消息内容不能超过500k
        int length = content.length();
        if(length>SIZE500K) {
            throw new BusinessException("消息大小不能超过500k");
        }

        long id = this.idService.get(KeyMessageId);

        Date timeNow = new Date();
        ChatMessageModel model = new ChatMessageModel();
        model.setId(id);
        model.setUsernameFrom(usernameSelf);
        model.setUsernameTo(usernameTo);
        model.setType(type);
        model.setContent(content);
        model.setCreateTime(timeNow);

        try {
            String json = OBJECTMAPPER.writeValueAsString(model);
            this.redisTemplate.opsForValue().set("message#" + id, json);
            this.redisTemplate.opsForList().rightPush("unreceived#" + usernameTo, String.valueOf(id));

            String keyTemporary = "notifyPullTimeout#" + usernameTo;
            Element element = this.cacheNotifyPullTimeout.get(keyTemporary);
            if(element==null) {
                element = new Element(keyTemporary, usernameTo);
                element.setTimeToLive(2);
                this.cacheNotifyPullTimeout.put(element);
                this.delayedService.notifyPullDelay(usernameTo);
            }

            SendMessageResultVO sendMessageResultVO = new SendMessageResultVO();
            sendMessageResultVO.setMessageId(id);
            sendMessageResultVO.setCreateTime(timeNow);
            return sendMessageResultVO;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("发送消息错误，联系管理员");
        }
    }

    public PageResponse<ChatMessageModel> pull(String usernameSelf) throws BusinessException {
        if(StringUtils.isEmpty(usernameSelf)) {
            throw new BusinessException("没有提供usernameSelf");
        }

        String keyTemporary = "unreceived#" + usernameSelf;
        int length = this.redisTemplate.opsForList().size(keyTemporary).intValue();
        if(length<=0) {
            PageResponse response = new PageResponse();
            response.setPageNum(1);
            response.setPageSize(SIZEPULLUNRECEIVEDBATCH);
            return response;
        }

        List<String> listTemp = this.redisTemplate.opsForList().range(keyTemporary, 0, SIZEPULLUNRECEIVEDBATCH-1);
        String [] sortedList = listTemp.toArray(new String[]{});
        Arrays.sort(sortedList);
        listTemp = Arrays.asList(sortedList);

        List<ChatMessageModel> messageList=new ArrayList<>();
        if(listTemp.size()>0){
            List<String> keysTemporary = new ArrayList<>();
            for(String idTemporary : listTemp) {
                String keyTemporary1 = "message#" + idTemporary;
                keysTemporary.add(keyTemporary1);
            }
            listTemp = keysTemporary;

            List<String> jsonMessages = this.redisTemplate.opsForValue().multiGet(listTemp);
            if(jsonMessages!=null && jsonMessages.size()>0) {
                for(String jsonMessage : jsonMessages) {
                    if(StringUtils.isEmpty(jsonMessage)) {
                        continue;
                    }

                    try {
                        ChatMessageModel model = OBJECTMAPPER.readValue(jsonMessage, ChatMessageModel.class);
                        messageList.add(model);
                    } catch (JsonProcessingException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }

        PageResponse<ChatMessageModel> response = new PageResponse<>();
        response.setPageSize(SIZEPULLUNRECEIVEDBATCH);
        response.setTotalRecord(length);
        response.setPageNum(1);
        response.setData(messageList);
        return response;
    }

    public void confirm(String usernameSelf,
                        List<Long> messageIds) throws BusinessException {
        if(StringUtils.isEmpty(usernameSelf)) {
            throw new BusinessException("没有提供usernameSelf");
        }
        if(messageIds==null || messageIds.size()<=0) {
            throw new BusinessException("没有提供消息id列表");
        }

        String key = "unreceived#" + usernameSelf;
        List<String> keysToDelete = new ArrayList<>();
        for(Long messageId : messageIds){
            this.redisTemplate.opsForList().remove(key, 1, String.valueOf(messageId));
            String keyTemporary = "message#" + messageId;
            keysToDelete.add(keyTemporary);
        }

        if(keysToDelete!=null && keysToDelete.size()>0) {
            this.redisTemplate.delete(keysToDelete);
        }
    }
}
