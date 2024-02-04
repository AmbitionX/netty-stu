package org.stu.netty.nio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author yupeng.zhang
 * @date 2024/1/19
 */
public class FileChannel {
    public static void main(String[] args) throws Exception {
        //创建的三种方式
        //第一种，通过Java本地实现的File获取通道
        //RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\project\\practice\\netty-stu\\server\\src\\main\\proto\\file.proto", "rw");
        //java.nio.channels.FileChannel accessFileChannel = randomAccessFile.getChannel();
        //ByteBuffer buf = ByteBuffer.allocate(15);
        //int read = accessFileChannel.read(buf);
        //while (read != -1) {
        //    //System.out.println("Read：" + read);
        //    buf.flip();
        //    if (buf.hasRemaining()) {
        //        System.out.print(new String(buf.array()));
        //    }
        //    buf.clear();
        //    read = accessFileChannel.read(buf);
        //}

        //第二种通过通过FileChannel提供的静态打开方法来直接使用，是Nio2针对各个通道提供的
        Path path = Paths.get("D:\\project\\practice\\netty-stu\\server\\src\\main\\proto\\file.proto");
        java.nio.channels.FileChannel fileChannel = java.nio.channels.FileChannel.open(path, StandardOpenOption.READ);



        //是nio2的files工具类提供的方法
        SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ);
    }
}
