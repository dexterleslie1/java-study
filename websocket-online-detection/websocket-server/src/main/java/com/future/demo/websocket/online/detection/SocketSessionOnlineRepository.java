package com.future.demo.websocket.online.detection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 *
 * @author dexterleslie@gmail.com
 */
public interface SocketSessionOnlineRepository extends JpaRepository<SocketSessionOnlineModel, Long> {
    /**
     *
     * @param sessionId
     * @param instanceId
     * @return
     */
    SocketSessionOnlineModel findBySessionIdAndInstanceId(String sessionId, String instanceId);

    /**
     *
     * @param time
     * @param instanceId
     * @return
     */
    List<SocketSessionOnlineModel> findByLatestActiveTimeLessThanEqualAndInstanceId(Date time, String instanceId);
}
