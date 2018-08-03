package com.future.study.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月2日
 * @time 下午9:32:37
 */
public class TestTCPClient {
	private final static Logger logger=LoggerFactory.getLogger(TestTCPClient.class);
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String []args){
		String host="localhost";
		int port=8080;
		
		Socket socket=null;
		try {
			socket=new Socket(host,port);
		} catch (UnknownHostException e) {
			logger.error("连接服务器失败 ",e);
		} catch (IOException e) {
			logger.error("连接服务器失败 ",e);
		}
		
		try{
			if(socket!=null){
				OutputStream outputStream=socket.getOutputStream();
				String clientMessage="我是客户端";
				for(int i=0;i<10000;i++){
					outputStream.write(clientMessage.getBytes());
				}
				
				byte []bytes=new byte[8192];
				InputStream inputStream=socket.getInputStream();
				int length=inputStream.read(bytes);
				String welcomeString=new String(bytes,0,length);
				logger.info(welcomeString);
			}
		}catch(IOException e){
			logger.error("客户端和服务器交互发生错误",e);
		}
		
		try {
			if(socket!=null){
				socket.close();
			}
		} catch (IOException e) {
			logger.error("关闭连接失败",e);
		}
		socket=null;
	}
}
