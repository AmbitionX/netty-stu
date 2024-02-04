package org.stu.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author yupeng.zhang
 * @date 2024/1/2
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {
        //使用 ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(5);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        int messageLength = 8;
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l;
                System.out.println("byteRead：" + byteRead);
                //使用流打印,看看当前的这个 buffer 的 position 和 limit
                Arrays.stream(byteBuffers).map(buffer -> "position = " + buffer.position() + ", limit = " + buffer.limit()).forEach(System.out::println);
            }

            //将所有buffer进行flip
            Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);
            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }

            Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);
            System.out.println("byteRead = " + byteRead + ", byteWrite = " + byteWrite + ", messagelength = " + messageLength);
        }

    }
}
