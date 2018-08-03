package com.future.study.socket.tcp.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监听8089端口接收关闭服务器命令
 * @author Dexterleslie
 * @date 2018年8月3日
 * @time 下午12:49:44
 */
public class TcpServerStopper {
	private final static Logger logger=LoggerFactory.getLogger(TcpServerStopper.class);
	
	private final static Integer PORT=8089;
	private TcpServer tcpServer;
	private ServerSocket serverSocket;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public TcpServerStopper(TcpServer tcpServer) throws IOException{
		if(tcpServer==null){
			throw new IllegalArgumentException("tcpServer不能null");
		}
		this.tcpServer=tcpServer;
		this.serverSocket=new ServerSocket();
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void start() throws IOException{
		this.serverSocket.bind(new InetSocketAddress(PORT));
		
		logger.info("TcpServerStopper服务已启动，端口："+this.serverSocket.getLocalPort());
		
		SocketWrapper wrapper=null;
		try{
			// 接收terminate命令则关闭服务器
			String command=null;
			while(StringUtils.isBlank(command)||!"terminate".equals(command)){
				try{
					wrapper=new SocketWrapper(this.serverSocket.accept());
					command=wrapper.read();
				}catch(EndOfStreamException e){
					// socket流结束抛出EndOfStreamException作为SocketWrapper流结束提示
				}finally{
					if(wrapper!=null){
						wrapper.close();
						wrapper=null;
					}
				}
			}
		}catch(IOException e){
			logger.error("处理socket异常",e);
		}finally{
			if(wrapper!=null){
				wrapper.close();
				wrapper=null;
			}
			// 任何情况TcpServerStopper退出都关闭TcpServer
			this.tcpServer.stop();
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				// 忽略异常
			}
		}
	}
}
