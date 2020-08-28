package com.future.demo.websocket.online.detection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 *
 * @author dexterleslie@gmail.com
 */
public interface SocketSessionConnectedRepository extends JpaRepository<SocketSessionConnectedModel, Long> {
    /**
     *
     * @param sessionId
     * @param instanceId
     * @return
     */
    SocketSessionConnectedModel findBySessionIdAndInstanceId(String sessionId, String instanceId);

    /**
     *
     * @param time
     * @return
     */
    List<SocketSessionConnectedModel> findByCreateTimeLessThanEqualAndInstanceId(Date time, String instanceId);
}
