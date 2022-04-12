package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {

    public static void main(String[] args) throws IOException {
        startServer();
    }

    static void startServer() throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(999));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();

                if (sk.isAcceptable()) {
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);

                } else if (sk.isReadable()) {

                    System.out.println("读事件!!!");
                    SocketChannel channel = (SocketChannel) sk.channel();
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(200);
                        //这里只读数据，未作任何处理
                        int result = channel.read(byteBuffer);
                        System.out.println(result);

                        if (result < 0) {
                            throw new IOException("guanbi");
                        }


                    } catch (IOException e) {
                        //手动关闭channel
                        System.out.println(e.getMessage());
                        System.out.println(e.getStackTrace());
                        sk.cancel();
                        if (channel != null)
                            channel.close();
                    }
                }


            }
        }
    }

}
