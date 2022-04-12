package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerSocketDemo {

    public static void main(String[] args) {
        try {


            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost", 9001));

            Socket socket = serverSocket.accept();
            socket.setSoTimeout(10*1000);

            InputStream inputStream = socket.getInputStream();

            while (true) {
                byte[] bytes = new byte[1000];

                int n = inputStream.read(bytes);

                System.out.println("读到了多少数据"+ n);
                System.out.println(new String(bytes, 0, n));
            }




//


//            Socket socket = serverSocket.accept();
//            System.out.println("接收到了一个连接");

//            System.in.read();

//            InputStream inputStream = socket.getInputStream();
//
//            byte[] bytes = new byte[1024*100];
//
//            while (true) {
//                Thread.sleep(1);
//
//                int len = inputStream.read(bytes);
//                if (len > 0)
//                    System.out.println(new String(bytes, 0, len));
//
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
