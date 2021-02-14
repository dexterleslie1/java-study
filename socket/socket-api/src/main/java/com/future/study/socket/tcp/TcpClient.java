package com.future.study.socket.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;
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
    private final static Logger logger=LoggerFactory.getLogger(TcpClient.class);

    private ExecutorService executorService= Executors.newFixedThreadPool(1);

	private SocketWrapper wrapper;
	private boolean isStopped=false;
	
	public TcpClient(String host,int port) throws IOException{
		Socket socket=new Socket(host,port);
		this.wrapper=new SocketWrapper(socket);
	}

	public void connect(){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    onMessage();
                } catch (EndOfStreamException e) {
                    // 忽略
                }
            }
        });
    }
    public void close(){
	    this.isStopped=true;
	    if(this.wrapper!=null){
	        this.wrapper.close();
	        this.wrapper=null;
        }
	}
	
	public void send(String type,String groupId,String message) throws IOException{
		JSONObject requestObject=new JSONObject();
		requestObject.put("type",type);
		requestObject.put("groupId",groupId);
		requestObject.put("message",message);
		String requestString=requestObject.toString();
		this.wrapper.send(requestString);
	}

	public void onMessage() throws EndOfStreamException{
	    while(!isStopped) {
            try {
                String message = this.wrapper.read();
                logger.info("客户端收到push消息："+message);
            } catch(SocketException e){
            	// 忽略
            }catch (IOException e) {
                logger.error("客户端处理服务器push消息失败",e);
            }
        }
    }
}
