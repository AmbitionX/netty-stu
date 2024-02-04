package org.stu.netty.netty.demo.case5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.stu.netty.netty.protobuf.FileOuterClass;

/**
 * @author yupeng.zhang
 * @date 2024/1/8
 */
public class NettyServer {

    public static void main(String[] args) {
        //创建BoosGroup和WorkerGroup
        //说明
        //1.创建两个线程组bossGroup和workerGroup
        //2.bossGroup只是处理链接请求，真正的客户端业务处理，会交给workGroup执行
        //3.两个都是无限循环
        //4.bossGroup和workGroup含有的子线程数（NioEventGroup）个数是默认cpu核心数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioSockerChannel作为通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到链接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    //.handler(null) //该handler对应bossGroup,childHandler对应workerGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象（匿名对象）
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //可以使用一个集合管理SocketChannel，再推送消息时，可以将业务加到各个channel对应的NIOEventLoop的taskQueue或者scheduleTaskQueue
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encoder", new ProtobufEncoder());
                            //指定对那种对象进行解码
                            pipeline.addLast("decode", new ProtobufDecoder(FileOuterClass.File.getDefaultInstance()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });//给我们的workGroup的EventLoop对应的管道设置处理器

            System.out.println("。。。服务器 is ready 。。。");

            //绑定一个端口并且同步，生成了一个ChannelFuture对象
            //启动服务器（并绑定端口）
            final ChannelFuture cf = bootstrap.bind(6668).sync();

            //给cf注册监听器，监控我们关心的时间

            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口 6668 成功");
                    } else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
