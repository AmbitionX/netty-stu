package org.stu.netty.nio.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yupeng.zhang
 * @date 2024/1/2
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\project\\practice\\netty-stu\\server\\src\\resources\\realFile01.txt");

        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();

        ////创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\project\\practice\\netty-stu\\server\\src\\resources\\file02.txt");
        FileChannel outFileChannel = fileOutputStream.getChannel();

        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            int read = fileChannel.read(byteBuffer);

            if (read == -1) {
                break;
            }

            byteBuffer.flip();
            //将buffer数据写入到fillerChannel
            outFileChannel.write(byteBuffer);
        }

        fileOutputStream.close();
        fileInputStream.close();
    }
}
