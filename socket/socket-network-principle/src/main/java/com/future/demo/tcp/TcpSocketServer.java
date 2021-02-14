package com.future.demo.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于tcp socket服务器
 */
public class TcpSocketServer {
    /**
     *
     */
    public static void main(String []args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Tcp socket服务器已启动，监听端口 " + port);

        boolean b = true;
        while(b) {
            Socket socket = serverSocket.accept();
            InetSocketAddress socketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();

            InputStream inputStream = socket.getInputStream();
            byte []bytes=new byte[1024];
            int length = inputStream.read(bytes);

            String message = new String(bytes, 0, length, "utf-8");
            System.out.println("来自客户端ip=" + socketAddress.getAddress() + ",port=" + socketAddress.getPort() + "的消息：" + message);
        }
        serverSocket.close();
    }
}
