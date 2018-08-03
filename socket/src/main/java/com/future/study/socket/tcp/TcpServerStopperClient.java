package com.future.study.socket.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.future.study.socket.tcp.server.SocketWrapper;

/**
 * TcpServerStopper客户端
 * 发送terminate命令到TcpServerStopper
 * @author Dexterleslie
 * @date 2018年8月3日
 * @time 下午1:27:23
 */
public class TcpServerStopperClient {
	private boolean isClosed=false;
	private SocketWrapper wrapper;
	
	/**
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * 
	 */
	public TcpServerStopperClient() throws UnknownHostException, IOException{
		Socket socket=new Socket("localhost",8089);
		try{
			wrapper=new SocketWrapper(socket);
		}catch(IOException e){
			if(wrapper!=null){
				wrapper.close();
				wrapper=null;
			}
			throw e;
		}
	}
	
	/**
	 * 发送关闭命令到TcpServerStopper关闭TcpServer
	 * @throws IOException 
	 */
	public void stopServer() throws IOException{
		this.checkIfClosedAndThrowIllegalStateException();
		this.wrapper.send("terminate");
	}
	
	/**
	 * 检查TcpServerStopperClient是否已关闭，是则抛出IllegalStateException
	 */
	private void checkIfClosedAndThrowIllegalStateException(){
		if(this.isClosed){
			throw new IllegalStateException("TcpServerStopperClient已关闭");
		}
	}
	
	/**
	 * 关闭TcpServerStopperClient
	 */
	public void close(){
		this.isClosed=true;
		if(this.wrapper!=null){
			this.wrapper.close();
			this.wrapper=null;
		}
	}
}
