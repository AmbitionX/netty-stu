package org.stu.netty.nio.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yupeng.zhang
 * @date 2024/1/2
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("D:\\project\\practice\\netty-stu\\server\\src\\resources\\realFile01.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ////创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\project\\practice\\netty-stu\\server\\src\\resources\\file02.txt");
        FileChannel outFileChannel = fileOutputStream.getChannel();

        outFileChannel.transferFrom(fileChannel, 0, fileChannel.size());


        fileChannel.close();
        outFileChannel.close();
        fileOutputStream.close();
        fileInputStream.close();
    }
}
