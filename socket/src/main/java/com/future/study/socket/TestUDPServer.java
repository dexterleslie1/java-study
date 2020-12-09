package com.future.study.socket;

import java.io.IOException;
import java.net.*;

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

			while(true) {
				byte[] bytes = new byte[1024];
				DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
				socket.receive(packet);
				String message = new String(packet.getData(), 0, packet.getLength());
				InetSocketAddress inetSocketAddress = (InetSocketAddress)packet.getSocketAddress();
				String hostFromClient = inetSocketAddress.getHostName();
				int portFromClient = inetSocketAddress.getPort();

				logger.info("服务器收到客户端 {}:{} 发来的消息 \"{}\"", hostFromClient, portFromClient, message);
			}
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
