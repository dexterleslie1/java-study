package com.future.study.socket.tcp;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试TcpServerStopperClient是否关闭TcpServer、TcpServerStopper
 * @author Dexterleslie
 * @date 2018年8月3日
 * @time 下午2:59:19
 */
public class TcpServerStopperClientTest {
	/**
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 * 
	 */
	@Test
	public void stopServer() throws InterruptedException, ExecutionException, IOException{
		ExecutorService executorService=Executors.newFixedThreadPool(1);
		Future<Object> future=executorService.submit(new Callable<Object>(){
			@Override
			public Object call() throws Exception{
				TcpServerStarter tcpServerStarter=new TcpServerStarter();
				tcpServerStarter.start();
				return null;
			}
		});
		
		// 等待3秒后关闭服务器
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// 忽略
		}
		
		// 检查8080、8089端口是否开启
		boolean isOpened=PortCheckerUtils.check("localhost", 8080);
		Assert.assertTrue("TcpServer没有启动",isOpened);
		isOpened=PortCheckerUtils.check("localhost", 8089);
		Assert.assertTrue("TcpServerStopper没有启动",isOpened);
		
		TcpServerStopperClient stopperClient=null;
		try{
			stopperClient=new TcpServerStopperClient();
			stopperClient.stopServer();
		}catch(IOException e){
			throw e;
		}finally{
			if(stopperClient!=null){
				stopperClient.close();
				stopperClient=null;
			}
		}
		
		// 检查TcpServerStarter是否有异常抛出
		future.get();
		
		// 检查TcpServerStopper是否关闭服务器
		isOpened=PortCheckerUtils.check("localhost", 8080);
		Assert.assertFalse("TcpServerStopper没有关闭TcpServer",isOpened);
		isOpened=PortCheckerUtils.check("localhost", 8089);
		Assert.assertFalse("TcpServerStopper没有关闭",isOpened);
	}
}
