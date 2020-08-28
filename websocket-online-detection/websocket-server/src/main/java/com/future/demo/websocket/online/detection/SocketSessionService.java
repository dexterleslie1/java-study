package com.future.demo.websocket.online.detection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dexterleslie@gmail.com
 */
@Service
public class SocketSessionService {
    private final static Logger logger = LoggerFactory.getLogger(SocketSessionService.class);

    @Value("${com.future.demo.websocket.online.detection.instanceId:}")
    private String instanceId;

    @Autowired
    SocketSessionConnectedRepository socketSessionConnectedRepository;
    @Autowired
    SocketSessionOnlineRepository socketSessionOnlineRepository;

    private Map<String, WebSocketSession> socketSessionConnectedMapper = new HashMap<>();
    private Map<String, WebSocketSession> socketSessionOnlineMapper = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        if(StringUtils.isEmpty(instanceId)) {
            throw new IllegalArgumentException("指定com.future.demo.websocket.online.detection.instanceId配置");
        }
    }

    /**
     * 新增connected状态session
     *
     * @param session
     */
    public void addConnected(WebSocketSession session) {
        String sessionId = session.getId();
        SocketSessionConnectedModel socketSessionConnectedModel =
                this.socketSessionConnectedRepository.findBySessionIdAndInstanceId(sessionId, instanceId);
        if(socketSessionConnectedModel==null) {
            socketSessionConnectedModel = new SocketSessionConnectedModel();
        }
        socketSessionConnectedModel.setSessionId(sessionId);
        socketSessionConnectedModel.setCreateTime(new Date());
        socketSessionConnectedModel.setInstanceId(instanceId);
        this.socketSessionConnectedRepository.save(socketSessionConnectedModel);
        if(this.socketSessionConnectedMapper.containsKey(sessionId)) {
            try {
                this.socketSessionConnectedMapper.get(sessionId).close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.socketSessionConnectedMapper.put(sessionId, session);
        logger.info("sessionId " + sessionId + " 连接服务器（" + instanceId + "）为connected状态，下一步应该登录，否则在30秒后被服务器强制断开连接");
    }

    /**
     * 删除connected状态session
     *
     * @param sessionId
     */
    public void removeConnected(String sessionId, boolean close) {
        SocketSessionConnectedModel sessionConnectedModel =
                this.socketSessionConnectedRepository.findBySessionIdAndInstanceId(sessionId, instanceId);
        if(sessionConnectedModel!=null) {
            this.socketSessionConnectedRepository.delete(sessionConnectedModel);
        }
        if(this.socketSessionConnectedMapper.containsKey(sessionId)) {
            try {
                if(close) {
                    this.socketSessionConnectedMapper.get(sessionId).close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.socketSessionConnectedMapper.remove(sessionId);
    }

    /**
     * 查询connected状态过期session
     *
     * @return
     */
    public List<SocketSessionConnectedModel> listConnectedExpired() {
        Date currentTime = new Date();
        Date time = new Date(currentTime.getTime() - 30*1000);
        List<SocketSessionConnectedModel> sessionConnectedModels =
                this.socketSessionConnectedRepository.findByCreateTimeLessThanEqualAndInstanceId(time, instanceId);
        return sessionConnectedModels;
    }

    /**
     * 释放connected状态过期session列表
     */
    public void doCronbRemoveConnectedExpired() {
        List<SocketSessionConnectedModel> sessionConnectedModels =
                this.listConnectedExpired();
        if(sessionConnectedModels!=null && sessionConnectedModels.size()>0) {
            for(SocketSessionConnectedModel socketSessionConnectedModel : sessionConnectedModels) {
                String sessionId = socketSessionConnectedModel.getSessionId();
                this.removeConnected(sessionId, true);
                logger.info("sessionId " + sessionId + " 被检测到30秒没有登录但持续连接服务器，被服务器（" + instanceId + "）强制断开连接");
            }
        }
    }

    private final static int TimeoutOnlineSeconds = 60;
    /**
     * 查询online状态过期session
     *
     * @return
     */
    public List<SocketSessionOnlineModel> listOnlineExpired() {
        Date currentTime = new Date();
        Date time = new Date(currentTime.getTime() - TimeoutOnlineSeconds*1000);
        List<SocketSessionOnlineModel> sessionOnlineModels =
                this.socketSessionOnlineRepository.findByLatestActiveTimeLessThanEqualAndInstanceId(time, instanceId);
        return sessionOnlineModels;
    }

    /**
     * 释放非正常关闭online状态过期session列表
     */
    public void doCronbRemoveOnlineExpired() {
        List<SocketSessionOnlineModel> sessionOnlineModels =
                this.listOnlineExpired();
        if(sessionOnlineModels!=null && sessionOnlineModels.size()>0) {
            for(SocketSessionOnlineModel socketSessionOnlineModel : sessionOnlineModels) {
                String sessionId = socketSessionOnlineModel.getSessionId();
                this.removeOnline(sessionId);
                logger.info("sessionId " + sessionId + " 被检测到" + TimeoutOnlineSeconds + "秒没有心跳，被服务器（" + instanceId + "）强制断开连接");
            }
        }
    }

    /**
     * 删除online socket session
     *
     * @param sessionId
     */
    public void removeOnline(String sessionId) {
        SocketSessionOnlineModel sessionOnlineModel =
                this.socketSessionOnlineRepository.findBySessionIdAndInstanceId(sessionId, instanceId);
        if(sessionOnlineModel!=null) {
            this.socketSessionOnlineRepository.delete(sessionOnlineModel);
        }
        if(this.socketSessionOnlineMapper.containsKey(sessionId)) {
            try {
                this.socketSessionOnlineMapper.get(sessionId).close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.socketSessionOnlineMapper.remove(sessionId);
    }

    /**
     * 新增online状态session
     *
     * @param session
     */
    public void addOnline(WebSocketSession session) {
        String sessionId = session.getId();
        this.removeConnected(sessionId, false);

        SocketSessionOnlineModel socketSessionOnlineModel =
                this.socketSessionOnlineRepository.findBySessionIdAndInstanceId(sessionId, instanceId);
        if(socketSessionOnlineModel==null) {
            socketSessionOnlineModel = new SocketSessionOnlineModel();
        }
        socketSessionOnlineModel.setSessionId(sessionId);
        socketSessionOnlineModel.setLatestActiveTime(new Date());
        socketSessionOnlineModel.setCreateTime(new Date());
        socketSessionOnlineModel.setInstanceId(instanceId);
        this.socketSessionOnlineRepository.save(socketSessionOnlineModel);
        socketSessionOnlineMapper.put(sessionId, session);
        logger.info("sessionId " + sessionId + " 登录服务器（" + instanceId + "），由connected状态变为online状态");
    }

    /**
     * 判断session是否已登录
     *
     * @param session
     * @return
     */
    public boolean isLogin(WebSocketSession session) {
        String sessionId = session.getId();
        return this.socketSessionOnlineMapper.containsKey(sessionId);
    }

    /**
     * 保持在线状态
     *
     * @param sessionId
     */
    public void keepOnline(String sessionId) {
        SocketSessionOnlineModel socketSessionOnlineModel =
                this.socketSessionOnlineRepository.findBySessionIdAndInstanceId(sessionId, instanceId);
        if(socketSessionOnlineModel!=null) {
            socketSessionOnlineModel.setLatestActiveTime(new Date());
            this.socketSessionOnlineRepository.save(socketSessionOnlineModel);

            try {
                if (this.socketSessionOnlineMapper.containsKey(sessionId)) {
                    WebSocketSession session = this.socketSessionOnlineMapper.get(sessionId);
                    session.sendMessage(new TextMessage("KEEPALIVE OK"));
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 判断用户是否在线，以决定是否推送离线消息
     *
     * @param sessionId
     * @return
     */
    public boolean isOnline(String sessionId) {
        // latestActiveTime 是否在TimeoutOnlineSeconds秒内
        SocketSessionOnlineModel socketSessionOnlineModel =
                this.socketSessionOnlineRepository.findBySessionIdAndInstanceId(sessionId, instanceId);
        if (socketSessionOnlineModel != null) {
            Date latestActiveTime = socketSessionOnlineModel.getLatestActiveTime();
            Date currentTime = new Date();
            long timeInterval = currentTime.getTime() - latestActiveTime.getTime();
            if (timeInterval <= TimeoutOnlineSeconds*1000) {
                return true;
            }
        }
        return false;
    }

    /**
     * 服务器推送消息到所有在线客户端
     *
     */
    public void doCronbPushMessage() {
        List<SocketSessionOnlineModel> socketSessionOnlineModels =
                this.socketSessionOnlineRepository.findAll();
        if(socketSessionOnlineModels!=null && socketSessionOnlineModels.size()>0) {
            for(SocketSessionOnlineModel socketSessionOnlineModel : socketSessionOnlineModels) {
                String sessionId = socketSessionOnlineModel.getSessionId();
                if(this.isOnline(sessionId)) {
                    WebSocketSession session = this.socketSessionOnlineMapper.get(sessionId);
                    try {
                        session.sendMessage(new TextMessage("sessionId " + sessionId + " 你好，服务器（" + instanceId + "）当前时间 " + new Date()));
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                } else {
                    logger.warn("sessionId " + sessionId + " 客户端不在线，推送离线消息，服务器（" + instanceId + "）");
                }
            }
        }
    }
}
