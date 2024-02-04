package org.stu.netty.netty.demo.case7;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.EmptyStackException;
import java.util.UUID;

/**
 *
 * 说明
 * 1. 我们自定义一个Handler需要继续netty规定好某个HandlerAdapter（规范）
 * 2. 这是我们自定义一个Handler，才能称为一个handler
 * @author yupeng.zhang
 * @date 2024/1/8
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    //处理异常，一半是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收到数据，并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("服务器收到消息如下");
        System.out.println("长度："+len);
        System.out.println("内容：" + new String(content, StandardCharsets.UTF_8));

        //回复消息
        String responseContent = UUID.randomUUID().toString();
        int responseLength = responseContent.getBytes(StandardCharsets.UTF_8).length;
        byte[] responseContent2 = responseContent.getBytes(StandardCharsets.UTF_8);
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLength);
        messageProtocol.setContent(responseContent2);
        ctx.writeAndFlush(messageProtocol);
    }
}
