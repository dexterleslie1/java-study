package com.future.study.socket.tcp.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月3日
 * @time 下午12:16:46
 */
public class SocketWrapper {
	private final static Logger logger=LoggerFactory.getLogger(SocketWrapper.class);
	
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	/**
	 * 
	 * @param socket
	 * @throws IOException 
	 */
	public SocketWrapper(Socket socket) throws IOException{
		if(socket==null){
			throw new IllegalArgumentException("socket对象不能null");
		}
		this.socket=socket;
		this.inputStream=this.socket.getInputStream();
		this.outputStream=this.socket.getOutputStream();
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 * @throws IOException 
	 */
	public void send(String message) throws IOException{
		if(StringUtils.isBlank(message)){
			return ;
		}
		byte []bytes=message.getBytes();
		this.outputStream.write(bytes);
	}
	
	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public String send(byte []bytes){
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String read() throws IOException{
		byte []bytes=new byte[1024];
		int length=this.inputStream.read(bytes);
		if(length==-1){
			return null;
		}
		String stringReturn=new String(bytes,0,length);
		return stringReturn;
	}
	
	/**
	 * 
	 */
	public void close(){
		if(this.socket!=null){
			try {
				this.socket.close();
			} catch (IOException e) {
				logger.error("关闭socket失败",e);
			}
			this.socket=null;
		}
	}
}
