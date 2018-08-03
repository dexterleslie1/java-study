package com.future.study.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月2日
 * @time 下午11:27:15
 */
public class TestUDPClient {
	private final static Logger logger=LoggerFactory.getLogger(TestUDPClient.class);
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String []args){
		DatagramSocket socket=null;
		try{
			socket=new DatagramSocket(0);
		}catch(IOException e){
			logger.error("UDP客户端启动失败",e);
		}
		
		try{
			String clientMessage="你好服务器";
			byte []bytes=clientMessage.getBytes();
			InetAddress inetAddress=InetAddress.getByName("localhost");
			DatagramPacket packet=new DatagramPacket(bytes,0,bytes.length,inetAddress,8080);
			socket.send(packet);
			
			bytes=new byte[1024];
			packet=new DatagramPacket(bytes,bytes.length);
			socket.receive(packet);
			String messageFromServer=new String(packet.getData());
			logger.error("服务器回应消息："+messageFromServer);
		}catch(IOException e){
			logger.error("客户端发送UDP数据包出错",e);
		}
		
		if(socket!=null){
			socket.close();
			socket=null;
		}
	}
}
