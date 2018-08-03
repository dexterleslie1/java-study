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
 * 
 * @author Dexterleslie
 * @date 2018年8月3日
 * @time 下午1:36:52
 */
public class TcpServerStarterTest {
	/**
	 * 测试TcpServerStarter是否启动TcpServer、TcpServerStopper
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test(timeout=10000)
	public void start() throws IOException, InterruptedException, ExecutionException{
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
	}
}
