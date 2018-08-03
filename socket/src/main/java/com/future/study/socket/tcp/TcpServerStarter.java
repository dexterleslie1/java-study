package com.future.study.socket.tcp;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.future.study.socket.tcp.server.TcpServer;
import com.future.study.socket.tcp.server.TcpServerStopper;

/**
 * 启动TcpServer、TcpServerStopper
 * @author Dexterleslie
 * @date 2018年8月3日
 * @time 下午1:14:42
 */
public class TcpServerStarter {
	private final static Logger logger=LoggerFactory.getLogger(TcpServerStarter.class);
	
	private ExecutorService executorService=Executors.newFixedThreadPool(1);
	
	public void start() throws IOException{
		TcpServer tcpServer=new TcpServer();
		TcpServerStopper tcpServerStopper=new TcpServerStopper(tcpServer);
		
		// 启动tcpServerStopper
		executorService.submit(new Runnable(){
			@Override
			public void run() {
				try {
					tcpServerStopper.start();
				} catch (IOException e) {
					logger.error("启动TcpServerStopper失败",e);
					throw new RuntimeException(e);
				}
			}
		});
		
		tcpServer.start();
	}
}
