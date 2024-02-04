package org.stu.netty.netty.demo.case5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.stu.netty.netty.protobuf.FileOuterClass;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author yupeng.zhang
 * @date 2024/1/8
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    //当通道就绪就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HelloWorldClientHandler Active");
        FileOuterClass.File file = FileOuterClass.File.newBuilder()
                .setName(String.format("客户端[%s]", ctx.channel().remoteAddress()))
                .setSize(ThreadLocalRandom.current().nextInt(100))
                .build();
        ctx.writeAndFlush(file);
    }

    //当通道有读取事件时，会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FileOuterClass.File file = (FileOuterClass.File) msg;
        System.out.println("收到服务器响应: " + file.getSize() + "--" + file.getName());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
