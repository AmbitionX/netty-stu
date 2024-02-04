package org.stu.netty.nio.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author yupeng.zhang
 * @date 2024/1/2
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\project\\practice\\netty-stu\\server\\src\\resources\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
