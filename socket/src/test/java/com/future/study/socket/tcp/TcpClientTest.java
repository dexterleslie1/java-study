package com.future.study.socket.tcp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.future.study.socket.tcp.server.EndOfStreamException;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月4日
 * @time 上午12:45:59
 */
public class TcpClientTest {
	@Test
	public void t1() throws UnknownHostException, IOException, EndOfStreamException{
		ExecutorService executorService=Executors.newFixedThreadPool(1);
		executorService.submit(new Callable<Object>(){
			@Override
			public Object call() throws Exception{
				TcpServerStarter tcpServerStarter=new TcpServerStarter();
				tcpServerStarter.start();
				return null;
			}
		});
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// 忽略
		}
		
		TcpClient client=new TcpClient("localhost",8080);
		client.send();
	}
}
