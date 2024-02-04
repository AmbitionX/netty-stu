package org.stu.netty.netty.dubbocase;

/**
 * @author yupeng.zhang
 * @date 2024/1/31
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
