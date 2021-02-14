package com.future.study.socket.tcp;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 连同性测试
 */
public class ConnectivityTests {
    /**
     *
     */
    @Test
    public void test() {
        String host = System.getenv("host");
        int port = Integer.parseInt(System.getenv("port"));
        boolean reachable;
        Socket socket=null;
        try{
            socket=new Socket();
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            socket.connect(socketAddress, 5000);
            reachable = true;
        }catch(IOException ex){
            ex.printStackTrace();
            reachable = false;
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

        String message;
        if(reachable) {
            message = "主机：" + host + "，port：" + port + " 可达";
        } else {
            message = "主机：" + host + "，port：" + port + " 不可达";
        }
        System.out.println(message);
    }
}
