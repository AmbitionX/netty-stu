package org.stu.netty.nio.basic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author yupeng.zhang
 * @date 2024/1/2
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        String str = "hello，世界\n欢迎来到2024";
        //创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\project\\practice\\netty-stu\\server\\src\\resources\\file01.txt");

        //通过fileOutputStream获取对应FileChannel
        //这个fileChannel真是类型是FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str放入byteBuffer
        byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));

        //对byteBuffer进行flip()
        byteBuffer.flip();

        //将buffer数据写入到fillerChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
