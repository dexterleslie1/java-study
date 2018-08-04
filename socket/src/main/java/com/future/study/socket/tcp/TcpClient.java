package com.future.study.socket.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.future.study.socket.tcp.server.EndOfStreamException;
import com.future.study.socket.tcp.server.SocketWrapper;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月2日
 * @time 下午9:32:37
 */
public class TcpClient {
	private SocketWrapper wrapper;
	
	public TcpClient(String host,int port) throws UnknownHostException, IOException{
		Socket socket=new Socket(host,port);
		this.wrapper=new SocketWrapper(socket);
	}
	
	public void send() throws IOException, EndOfStreamException{
		String str="{";
		this.wrapper.send(str);
		String response=this.wrapper.read();
		System.out.println(response);
	}
}
