package com.future.demo.chat.server.value.object;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 发送消息成功返回结果
 */
public class SendMessageResultVO {
    private long messageId = 0;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = null;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
