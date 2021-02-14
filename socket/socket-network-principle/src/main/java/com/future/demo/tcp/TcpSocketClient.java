package com.future.demo.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 基于tcp socket客户端
 */
public class TcpSocketClient {
    /**
     *
     * @throws IOException
     */
    public static void main(String []args) throws IOException {
        String host = "192.168.3.2";
        int port = 8080;

        SocketAddress socketAddress = new InetSocketAddress(host, port);
        Socket socket = new Socket();
        socket.connect(socketAddress);

        String message = "你好，我是Dexter";
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes("utf-8"));
        outputStream.flush();
        socket.close();
        System.out.println();
    }
}
