package com.future.study.socket.tcp.server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月3日
 * @time 下午5:03:27
 */
public class SocketThread {
	private final static Logger logger=LoggerFactory.getLogger(SocketThread.class);
	
	private ExecutorService executorService=Executors.newFixedThreadPool(1);
	
	private SocketWrapper wrapper;
	private boolean isStopped=false;
	
	/**
	 * 
	 * @param socket
	 * @throws IOException 
	 */
	public SocketThread(Socket socket) throws IOException{
		this.wrapper=new SocketWrapper(socket);
	}
	
	/**
	 * 启动Socket处理线程 
	 */
	public void start(){
		// 显示远程客户端连接信息
		String clientInfo=this.wrapper.getInfo();
		logger.info("客户端连接服务器："+clientInfo);
		
		executorService.submit(new Runnable(){
			@Override
			public void run() {
				while(!isStopped){
					try {
						// 读取客户端请求
						String requestString=wrapper.read();
						String responseString="{}";
						try{
							JSONObject requestObject=new JSONObject(requestString);
							boolean hasType=requestObject.has("type");
							if(!hasType){
								JSONObject responseObject=new JSONObject();
								responseObject.put("errorCode", 5000);
								responseObject.put("errorMessage", "错误请求，请指定请求类型");
								responseString=responseObject.toString();
							}else{
								JSONObject responseObject=new JSONObject();
								responseObject.put("welcome","欢迎连接TcpServer服务器");
								responseString=responseObject.toString();
							}
						}catch(JSONException e){
							JSONObject responseObject=new JSONObject();
							responseObject.put("errorCode", 5000);
							responseObject.put("errorMessage", "请求数据错误，不符合json格式");
							responseString=responseObject.toString();
						}finally{
							SocketThread.this.wrapper.send(responseString);
						}
					} catch (IOException e) {
						logger.error("服务器处理客户端请求失败",e);
					} catch (EndOfStreamException e){
						isStopped=true;
						break;
					}
				}
			}});
	}
	
	/**
	 * 停止SocketThread线程
	 */
	public void close(){
		this.isStopped=true;
		if(this.wrapper!=null){
			this.wrapper.close();
			this.wrapper=null;
		}
	}
}
