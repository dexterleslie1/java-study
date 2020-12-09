package com.future.study.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

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
		String host = System.getenv("host");

		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(0);
			int portLocal = socket.getLocalPort();
			logger.info("UDP客户端已打开本地端口 {} 准备和UDP服务器通讯", portLocal);

			String message = "你好服务器";
			byte[] bytes = message.getBytes();
			InetAddress inetAddress = InetAddress.getByName(host);
			DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, inetAddress, 8080);
			socket.send(packet);

			InetSocketAddress socketAddress = (InetSocketAddress) packet.getSocketAddress();
			logger.info("UDP客户端发送消息 \"{}\" 给远程服务器 {}:{}", message, socketAddress.getAddress().getHostAddress(), socketAddress.getPort());

			bytes = new byte[1024];
			packet = new DatagramPacket(bytes, bytes.length);
			socket.receive(packet);
			message = new String(packet.getData(), 0, packet.getLength());
			InetSocketAddress inetSocketAddress = (InetSocketAddress)packet.getSocketAddress();
			String hostname = inetSocketAddress.getHostName();
			int port = inetSocketAddress.getPort();
			logger.info("UDP客户端收到UDP服务器回复 {}:{} 消息 \"{}\"", hostname, port, message);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (socket != null) {
				socket.close();
				socket = null;
			}
		}
	}
}
