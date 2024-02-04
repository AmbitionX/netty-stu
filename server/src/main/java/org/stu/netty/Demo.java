package org.stu.netty;

import io.netty.handler.codec.base64.Base64Encoder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author yupeng.zhang
 * @date 2024/1/4
 */
public class Demo {
    public static void main(String[] args) throws Exception {

        FutureTask<String> futureTask = new FutureTask<>(() -> {
            Thread.sleep(2000);
            return "ok";
        });
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(futureTask);
        System.out.println("invoke 1");
        System.out.println(futureTask.get());

        ServerSocket serverSocket = new ServerSocket();
        try {
            serverSocket.bind(new InetSocketAddress(3333));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(Base64.getEncoder().encodeToString("cardQu:@W#E$R%T^Y".getBytes(StandardCharsets.UTF_8)));

        //long start = System.currentTimeMillis();
        //Thread.sleep(2000);
        //System.out.println((System.currentTimeMillis()-start)/1000);
    }
}
