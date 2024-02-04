package org.stu.netty.nio.channel.case01;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author yupeng.zhang
 * @date 2024/1/19
 */
public class Main {

    public static void main(String[] args) throws Exception {
        FileInputStream file1 = new FileInputStream("D:\\project\\practice\\netty-stu\\server\\src\\main\\proto\\file.proto");
        FileOutputStream file2 = new FileOutputStream("D:\\project\\practice\\netty-stu\\server\\src\\main\\proto\\file1.proto");

        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File("file.txt")));
        dataInputStream.close();

        System.out.println(dataInputStream.readUTF());

        //1.获取通道
        FileChannel inChannel = file1.getChannel();
        FileChannel outChannel = file2.getChannel();

        //2.分配缓冲区
        ByteBuffer buf = ByteBuffer.allocate(12);

        //3。用通道传输数据
        while (inChannel.read(buf) != -1) {
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

    }
}
