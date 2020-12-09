package com.future.study.socket;

import java.io.IOException;
import java.net.*;
import java.util.Date;

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
		int port = 8080;
		DatagramSocket socket = null;
		try{
			socket = new DatagramSocket(port);

			InetSocketAddress inetSocketAddressLocal = (InetSocketAddress)socket.getLocalSocketAddress();
			String canonicalHostNameLocal = inetSocketAddressLocal.getAddress().getHostAddress();
			int portLocal = inetSocketAddressLocal.getPort();
			logger.info("UDP服务已启动，本地监听 {}:{}", canonicalHostNameLocal, portLocal);

//			while(true) {
				byte[] bytes = new byte[1024];
				DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
				socket.receive(packet);
				String message = new String(packet.getData(), 0, packet.getLength());
				InetSocketAddress inetSocketAddressClient = (InetSocketAddress)packet.getSocketAddress();
				String hostFromClient = inetSocketAddressClient.getHostName();
				int portFromClient = inetSocketAddressClient.getPort();

				logger.info("UDP服务器收到客户端 {}:{} 消息 \"{}\"", hostFromClient, portFromClient, message);
//			}

			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				//
			}

			Date currentTime = new Date();
			message = currentTime.toString();
			bytes = message.getBytes("utf8");
			packet = new DatagramPacket(bytes, bytes.length, inetSocketAddressClient);
			socket.send(packet);
			inetSocketAddressClient = (InetSocketAddress)packet.getSocketAddress();
			logger.info("UDP服务器回复客户端 {}:{} 消息 \"{}\"", inetSocketAddressClient.getHostName(), inetSocketAddressClient.getPort(), message);
		}catch(IOException e){
			logger.error(e.getMessage(), e);
		} finally {
			if(socket!=null){
				socket.close();
				socket=null;
			}
		}
	}
}
