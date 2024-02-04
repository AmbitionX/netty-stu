package org.stu.netty.netty.demo.case6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.stu.netty.netty.protobuf.FileOuterClass;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * 说明
 * 1. 我们自定义一个Handler需要继续netty规定好某个HandlerAdapter（规范）
 * 2. 这是我们自定义一个Handler，才能称为一个handler
 * @author yupeng.zhang
 * @date 2024/1/8
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    //处理异常，一半是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        //将buffer转成字符串
        String message = new String(buffer, CharsetUtil.UTF_8);

        System.out.println("服务器接收到数据 " + message);
        System.out.println("服务器接收到消息量=" + (++this.count));

        //服务器回送数据给客户端, 回送一个随机id ,
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID() + " ", CharsetUtil.UTF_8);
        ctx.writeAndFlush(responseByteBuf);
    }
}
