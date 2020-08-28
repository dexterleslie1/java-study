package com.future.demo.websocket.online.detection;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * @author dexterleslie@gmail.com
 */
@Data
@Entity
@Table(name="socket_session_connected")
public class SocketSessionConnectedModel {
    @Id
    private long id;
    private String sessionId;
    private String instanceId;
    private Date createTime;
}
