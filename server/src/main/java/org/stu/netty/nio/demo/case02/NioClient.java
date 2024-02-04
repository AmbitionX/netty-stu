package org.stu.netty.nio.demo.case02;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author yupeng.zhang
 * @date 2024/1/3
 */
public class NioClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        String fileName = "D:\\project\\practice\\netty-stu\\server\\src\\resources\\test.log";
        //得到一个文件fileChannel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        //准备发送
        long start = System.currentTimeMillis();
        //在linux下一个transferTo方法就可以完成传输
        //在 windows 下一次调用 transferTo 只能发送 8m, 就需要分段传输文件,而且要主要
        //传输时的位置=》课后思考...
        //transferTo 底层使用到零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.printf("发送的总字节数 = %s ，耗时：%s", transferCount, (System.currentTimeMillis() - start));

        fileChannel.close();
    }
}
