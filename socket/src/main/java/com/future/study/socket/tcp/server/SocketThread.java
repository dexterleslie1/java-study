package com.future.study.socket.tcp.server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月3日
 * @time 下午5:03:27
 */
public class SocketThread {
	private final static Logger logger=LoggerFactory.getLogger(SocketThread.class);
	
	private ExecutorService executorService=Executors.newFixedThreadPool(1);
	
	private SocketWrapper wrapper;
	private boolean isStopped=false;
	
	/**
	 * 
	 * @param socket
	 * @throws IOException 
	 */
	public SocketThread(Socket socket) throws IOException{
		this.wrapper=new SocketWrapper(socket);
	}
	
	/**
	 * 启动Socket处理线程 
	 */
	public void start(){
		// 显示远程客户端连接信息
		String clientInfo=this.wrapper.getInfo();
		logger.info("客户端连接服务器："+clientInfo);
		
		executorService.submit(new Runnable(){
			@Override
			public void run() {
				while(!isStopped){
					try {
						String string=wrapper.read();
//						if(!StringUtils.isBlank(string)){
							logger.info("客户端消息："+string);
//						}
					} catch (IOException e) {
						logger.error("服务器处理客户端请求失败",e);
					} catch (EndOfStreamException e){
						isStopped=true;
						break;
					}
				}
			}});
	}
	
	/**
	 * 停止SocketThread线程
	 */
	public void close(){
		this.isStopped=true;
		if(this.wrapper!=null){
			this.wrapper.close();
			this.wrapper=null;
		}
	}
}
