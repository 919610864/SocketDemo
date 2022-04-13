package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class NioServerDemo {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(8082));
        //设置非阻塞
        channel.configureBlocking(false);

        //注册
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);

        //处理器
        Handler handler = new Handler(1024);
        while (true) {
            //等待请求，每次等待阻塞5s，5s后线程继续向下执行，如果传入0或者不传参数将一直阻塞
            if (selector.select(5000) == 0) {
                continue;
            }

            //获取待处理的SelectionKey
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                try {
                    SelectionKey key = keyIterator.next();
                    //当接受到请求
                    if (key.isAcceptable()) {
                        handler.handleAccept(key);
                    }
                    //读数据
                    if (key.isReadable()) {
                        handler.handleRead(key);
                    }
                } catch (IOException e) {
                    keyIterator.remove();
                    e.printStackTrace();
                }
                keyIterator.remove();
            }
        }
    }

    private static class Handler {
        private int    bufferSize   = 1024;
        private String localCharset = "UTF-8";

        public Handler() {
        }

        public Handler(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public Handler(String localCharset) {
            this.localCharset = localCharset;
        }

        public Handler(int bufferSize, String localCharset) {
            this.bufferSize = bufferSize;
            this.localCharset = localCharset;
        }

        public void handleAccept(SelectionKey selectionKey) {
            try {
                SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
                socketChannel.configureBlocking(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void handleRead(SelectionKey key) throws IOException {
            //获取channel
            SocketChannel channel = (SocketChannel) key.channel();
            //获取buffer 重置
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            if (channel.read(buffer) == -1) {
                channel.close();
            } else {
                //转为读状态
                buffer.flip();
                String receivedString = Charset.forName(localCharset)
                        .newDecoder().decode(buffer).toString();

                System.out.printf("接受到客户端数据" + receivedString);

                //返回数据给客户端
                String sendString = "接受数据：" + receivedString;
                buffer = ByteBuffer.wrap(sendString.getBytes(localCharset));
                channel.write(buffer);

                channel.close();
            }
        }
    }
}
