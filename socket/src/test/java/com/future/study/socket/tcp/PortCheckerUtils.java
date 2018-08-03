package com.future.study.socket.tcp;

import java.io.IOException;
import java.net.Socket;

/**
 * 检查端口是否开启
 * @author Dexterleslie
 * @date 2018年8月3日
 * @time 下午2:47:17
 */
public class PortCheckerUtils {
	/**
	 * 检查端口是否开启
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean check(String host,int port){
		Socket socket=null;
		try{
			socket=new Socket(host,port);
		}catch(IOException ex){
			return false;
		}finally{
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					// 忽略异常
				}
				socket=null;
			}
		}
		return true;
	}
}
