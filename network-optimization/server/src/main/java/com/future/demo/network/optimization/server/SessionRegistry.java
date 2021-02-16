package com.future.demo.network.optimization.server;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SessionRegistry {
    private List<WebSocketSession> sessionList = new ArrayList<>();

    /**
     *
     * @param session
     */
    public void add(WebSocketSession session) {
        this.sessionList.add(session);
    }

    /**
     *
     * @param session
     */
    public void remove(WebSocketSession session) {
        this.sessionList.remove(session);
    }

    /**
     *
     * @return
     */
    public List<WebSocketSession> getSessionList() {
        return this.sessionList;
    }
}
