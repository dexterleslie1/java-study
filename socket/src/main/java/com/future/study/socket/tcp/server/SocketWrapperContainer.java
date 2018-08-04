package com.future.study.socket.tcp.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存客户端对应的SocketWrapper
 * @author Dexterleslie
 * @date 2018年8月4日
 * @time 上午12:23:35
 */
public class SocketWrapperContainer {
	private Map<String,SocketWrapper> container=new HashMap<String,SocketWrapper>();
	
	public void add(String clientId,SocketWrapper socketWrapper){
		this.container.put(clientId, socketWrapper);
	}
	
	public SocketWrapper get(String clientId){
		return this.container.get(clientId);
	}
}
