package com.future.study.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月2日
 * @time 下午11:16:53
 */
public class TestUDPServer {
	private final static Logger logger=LoggerFactory.getLogger(TestUDPServer.class);
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String []args){
		int port=8080;
		DatagramSocket socket=null;
		try {
			socket=new DatagramSocket(port);
		} catch (SocketException e) {
			logger.error("UDP服务器启动失败",e);
		}
		
		try{
			byte []bytes=new byte[1024];
			DatagramPacket packet=new DatagramPacket(bytes,bytes.length);
			socket.receive(packet);
			String messageFromClient=new String(packet.getData());
			logger.info("服务器收到客户端发来的消息："+messageFromClient);
			
			String welcomeString="欢迎连接服务器";
			bytes=welcomeString.getBytes();
			packet=new DatagramPacket(bytes,0,bytes.length,packet.getAddress(),packet.getPort());
			socket.send(packet);
		}catch(IOException e){
			logger.error("服务器接收UDP数据包出错",e);
		}
		
		if(socket!=null){
			socket.close();
			socket=null;
		}
	}
}
