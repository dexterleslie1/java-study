package com.future.study.socket.tcp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存客户端对应的SocketWrapper
 * @author Dexterleslie
 * @date 2018年8月4日
 * @time 上午12:23:35
 */
public class SocketWrapperContainer {
    private final static SocketWrapperContainer instance=new SocketWrapperContainer();

	private Map<String,List<SocketWrapper>> container=new HashMap<String,List<SocketWrapper>>();

    /**
     *
     */
	private SocketWrapperContainer(){

    }

    public static SocketWrapperContainer getIntance(){
	    return instance;
    }
	
	public void add(String groupId,SocketWrapper socketWrapper){
		if(!container.containsKey(groupId)){
		    container.put(groupId,new ArrayList<SocketWrapper>());
        }
        List<SocketWrapper> wrappers=this.container.get(groupId);
		wrappers.add(socketWrapper);
	}
	
	public List<SocketWrapper> get(String groupId){
		return this.container.get(groupId);
	}
}
