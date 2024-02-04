package org.stu.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yupeng.zhang
 * @date 2024/1/2
 */
public class BIOServer {
    public static void main(String[] args) throws Exception{
        //线程池机制
        /*
         * 思路
         * 1.创建一个线程池
         * 2.如果有客户端链接，就创建一个线程，与之通信（单独写一个方法）
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {
            System.out.println("线程信息id："+Thread.currentThread().getId()+"，名字："+Thread.currentThread().getName());
            //监听，等待客户端链接
            System.out.println("等待连接。。。。。。");
            Socket socket = serverSocket.accept();
            //就创建一个链接，与之通信
            executorService.submit(()->{
                handle(socket);
            });
        }
    }

    //和客户端通信
    private static void handle(Socket socket) {
        System.out.println("线程信息id："+Thread.currentThread().getId()+"，名字："+Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        //通过socket获取流
        try {
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端发送的信息
            while (true) {
                System.out.println("线程信息id："+Thread.currentThread().getId()+"，名字："+Thread.currentThread().getName());
                System.out.println("read。。");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client的链接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
