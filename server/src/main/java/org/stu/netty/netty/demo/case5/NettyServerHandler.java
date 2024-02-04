package org.stu.netty.netty.demo.case5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import org.stu.netty.netty.protobuf.FileOuterClass;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * 说明
 * 1. 我们自定义一个Handler需要继续netty规定好某个HandlerAdapter（规范）
 * 2. 这是我们自定义一个Handler，才能称为一个handler
 * @author yupeng.zhang
 * @date 2024/1/8
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HelloWorldServerHandler active");
    }

//读取数据实际（这里我们可以读取客户端发送的消息）
    /**
     * 1.ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道chanel，地址
     * 2.Object msg：就是客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead...");
        // 读取客户端发送的数据 UserMOdel.User
        FileOuterClass.File file = (FileOuterClass.File) msg;
        System.out.println("客户端发送的数据: " + file.getName() + "--" + file.getSize());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFklush 是write + flush
        //将数据写入到缓存，并刷新
        //一般讲，我们对这个发送的数据进行编码
        FileOuterClass.File file = FileOuterClass.File.newBuilder()
                .setName(String.format("服务端[%s]", ctx.channel().remoteAddress()))
                .setSize(ThreadLocalRandom.current().nextInt(100))
                .build();
        ctx.writeAndFlush(file);
    }

    //处理异常，一半是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
