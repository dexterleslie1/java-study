package com.future.study.socket.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * tcp服务器
 * @author Dexterleslie
 * @date 2018年8月2日
 * @time 下午9:21:37
 */
public class TcpServer {
	private final static Logger logger=LoggerFactory.getLogger(TcpServer.class);
	
	private final static Integer PORT=8080;
	private ServerSocket serverSocket;
	private boolean isStopped=false;
	
	/**
	 * 
	 * @throws IOException 
	 */
	public TcpServer() throws IOException{
		this.serverSocket=new ServerSocket(PORT);
	}
	
	/**
	 * 
	 */
	public void start(){
		logger.info("TcpServer服务已启动，端口："+this.serverSocket.getLocalPort());
		while(!isStopped){
			try{
				if(serverSocket!=null){
					SocketWrapper wrapper=new SocketWrapper(serverSocket.accept());
					wrapper.close();
				}
			}catch(IOException e){
				// 忽略
			}
		}
	}
	
	/**
	 * 关闭服务器
	 */
	public void stop(){
		this.isStopped=true;
		if(this.serverSocket!=null){
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				logger.error("关闭TcpServer失败",e);
			}
			this.serverSocket=null;
		}
	}
}
