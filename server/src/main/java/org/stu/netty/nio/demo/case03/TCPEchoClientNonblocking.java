package org.stu.netty.nio.demo.case03;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author yupeng.zhang
 * @date 2024/1/17
 */
public class TCPEchoClientNonblocking {
    private final static String host = "127.0.0.1";
    private final static int port = 7001;

    public static void main(String[] args) throws Exception {
        byte[] sendMsg = "洞幺洞幺我是洞拐".getBytes(StandardCharsets.UTF_8);
        SocketChannel cletchan = SocketChannel.open();
        cletchan.configureBlocking(false);
        if (!cletchan.connect(new InetSocketAddress(host, port))) {
            while (!cletchan.finishConnect()) {
                System.out.println(".");
            }
        }
        System.out.println();
        ByteBuffer writeBuf = ByteBuffer.wrap(sendMsg);
        ByteBuffer readBuf = ByteBuffer.allocate(sendMsg.length);
        int totalBytesRcvd = 0;
        int bytesRcvd;
        while (totalBytesRcvd < sendMsg.length) {
            if (writeBuf.hasRemaining()) {
                cletchan.write(writeBuf);
            }
            if ((bytesRcvd = cletchan.read(readBuf)) == -1) {
                throw new SocketException("Connect close prematurely");
            }
            //计算总字节数
            totalBytesRcvd += bytesRcvd;
            System.out.println(".");
        }
        System.out.println("Received：" + new String(readBuf.array(), 0, totalBytesRcvd));
        cletchan.close();
    }

}
