package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TraditionalSocketClient {

    public static void main(String[] args) throws IOException {

        startClient();
    }
    static void startClient() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(999));
//        socket.getOutputStream().write(new byte[100]);
        //要注意这个close方法，这是正常关闭socket的方法
        //也是导致这个错误的根源
//        socket.close();

        System.in.read();
    }

}
