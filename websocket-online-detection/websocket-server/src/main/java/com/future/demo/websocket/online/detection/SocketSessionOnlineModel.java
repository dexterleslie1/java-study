package com.future.demo.websocket.online.detection;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户在线模型
 *
 * @author dexterleslie@gmail.com
 */
@Data
@Entity
@Table(name="socket_session_online")
public class SocketSessionOnlineModel {
    @Id
    private long id;
    private String sessionId;
    /**
     * 微服务环境考虑到多个socket服务器，sessionId会重复，需要使用instanceId标识
     */
    private String instanceId;
    private Date latestActiveTime;
    private Date createTime;
}
