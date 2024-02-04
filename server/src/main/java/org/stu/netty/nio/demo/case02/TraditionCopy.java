package org.stu.netty.nio.demo.case02;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yupeng.zhang
 * @date 2024/1/3
 */
public class TraditionCopy {

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\project\\practice\\netty-stu\\server\\src\\resources\\transfer1.zip");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        byte[] arr = new byte[(int) file.length()];

        raf.read(arr);

        Socket socket = new ServerSocket(8080).accept();
        socket.getOutputStream().write(arr);
    }
}
