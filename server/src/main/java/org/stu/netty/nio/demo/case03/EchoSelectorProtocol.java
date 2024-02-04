package org.stu.netty.nio.demo.case03;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author yupeng.zhang
 * @date 2024/1/17
 */
public class EchoSelectorProtocol implements TCPProtocol {
    private int bufSize;

    public EchoSelectorProtocol(int bufSize) {
        this.bufSize = bufSize;
    }

    public static void main(String[] args) {
        System.out.println(((1|4)&~16));
    }

    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
        channel.configureBlocking(false);
        //将选择器注册到连接到的客户端信道，并制定该信道的key值为OP_READ，同时为该信道指定关联的附件
        channel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        //获取改信道关联的附件，这里为缓冲区
        ByteBuffer buf = (ByteBuffer) key.attachment();
        int bytesRead = channel.read(buf);
        //如果read（）为-1，说明客户端关闭了连接，那么客户端已经接收到了与自己发送字节数相等的数据，可以安全关闭
        if (bytesRead == -1) {
            channel.close();
        } else {
            //如果缓冲区总读入了数据，则将该信道的感兴趣设为可读可写
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer byteBuf = (ByteBuffer) key.attachment();
        byteBuf.flip();
        SocketChannel channel = (SocketChannel) key.channel();
        channel.write(byteBuf);
        if (!byteBuf.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ);
        }
        byteBuf.compact();
    }
}
