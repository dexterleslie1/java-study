package com.future.demo.chat.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;


@Data
public class ChatMessageModel {
    // 消息id
    private long id=0;
    // 消息发送人
    private String usernameFrom;
    // 消息接收人
    private String usernameTo;
    // 消息内容
    private String content;
    // 消息类型(1.1vs1文字 2.1vs1语音 3.1vs1图片 4.群消息文字 5.群消息图片 6.群消息语音)
    private int type=0;
    // 消息发送时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("createTime")
    private Date createTime=null;
}
