package com.future.demo.chat.server.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.demo.chat.server.common.ObjectResponse;
import com.future.demo.chat.server.common.PageResponse;
import com.future.demo.chat.server.exception.BusinessException;
import com.future.demo.chat.server.model.ChatMessageModel;
import com.future.demo.chat.server.value.object.SendMessageResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Tests {
    private final static Random RANDOM = new Random();
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void test() throws ExecutionException, InterruptedException, IOException, TimeoutException, BusinessException {
        String apiHost = System.getenv("apiHost");

        String usernameA = "A用户";
        RestApi restApiA = null;
        String usernameB = "B用户";
        RestApi restApiB = null;

        try {
            restApiA = new RestApi(usernameA, apiHost);
            restApiB = new RestApi(usernameB, apiHost);

            clearMessage(restApiB);

            Map<Long, ChatMessageModel> mapMessagePulled = new HashMap<>();
            final RestApi restApiFinal = restApiB;
            restApiB.registerOnMessageCallback(new RestApi.OnMessageCallback() {
                @Override
                public void onMessage(String message) throws BusinessException {
                    ObjectResponse<JsonNode> response1 = null;
                    try {
                        response1 = OBJECT_MAPPER.readValue(message, new TypeReference<ObjectResponse<JsonNode>>() {});
                    } catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                    }
                    if(response1==null) {
                        return;
                    }
                    JsonNode node = response1.getData();
                    if(node==null || !node.has("action")) {
                        return;
                    }
                    String action = node.get("action").asText();
                    if(!"pull".equals(action)) {
                        return;
                    }

                    PageResponse<ChatMessageModel> response = restApiFinal.pull();
                    while(response.getTotalPage()>0) {
                        List<ChatMessageModel> modelList = response.getData();
                        List<Long> messageIdsToConfirm = new ArrayList<>();
                        for(ChatMessageModel model : modelList) {
                            long idTemporary1 = model.getId();
                            messageIdsToConfirm.add(idTemporary1);
                            mapMessagePulled.put(idTemporary1, model);
                        }

                        restApiFinal.confirm(messageIdsToConfirm);
                        response = restApiFinal.pull();
                    }
                }
            });

            restApiA.connectSocket();
            restApiB.connectSocket();

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

                    ObjectResponse<SendMessageResultVO> response = restApiA.send(usernameB, content, type);
                    long messageId = response.getData().getMessageId();

                    ChatMessageModel modelTemporary = new ChatMessageModel();
                    modelTemporary.setId(messageId);
                    modelTemporary.setUsernameFrom(restApiA.getUsername());
                    modelTemporary.setUsernameTo(usernameB);
                    modelTemporary.setType(type);
                    modelTemporary.setContent(content);
                    modelTemporary.setCreateTime(response.getData().getCreateTime());
                    mapMessageSend.put(messageId, modelTemporary);
                }
            }

            Thread.sleep(20000);

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
        } catch(Exception ex) {
            throw ex;
        } finally {
            if(restApiA!=null) {
                restApiA.disconnectSocket();
            }
            if(restApiB!=null) {
                restApiB.disconnectSocket();
            }
        }
        Thread.sleep(1000);
    }

    void clearMessage(RestApi restApi) throws BusinessException {
        PageResponse<ChatMessageModel> response = restApi.pull();
        while(response.getTotalPage()>0) {
            List<ChatMessageModel> modelList = response.getData();
            List<Long> messageIdsToConfirm = new ArrayList<>();
            for(ChatMessageModel model : modelList) {
                long idTemporary1 = model.getId();
                messageIdsToConfirm.add(idTemporary1);
            }

            restApi.confirm(messageIdsToConfirm);
            response = restApi.pull();
        }
    }
}
