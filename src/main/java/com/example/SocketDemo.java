package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketDemo {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhsot", 9001); // --- >tcp


//            Socket socket = new Socket("localhost", 8081); // --- >socket()


            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(oneHttp().getBytes()); // close socket连接
            outputStream.write(oneHttp().getBytes());
            outputStream.write(oneHttp().getBytes());

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[2*1024];
            int len;
            while ((len = inputStream.read(bytes, 0, bytes.length)) > 0) {
                System.out.println(new String(bytes,0,len));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // http1.0
    // 数据之后，协议解析
    public static String oneHttp() {
        // 请求行
        StringBuffer sb = new StringBuffer("GET /HelloServlet/servletDemo HTTP/1.1\r\n");
        // 请求头
        sb.append("Host: localhost:8080\r\n");

//        // 结束请求头
//        sb.append("123");




        return sb.toString();
    }
}
