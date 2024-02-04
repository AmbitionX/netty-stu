package org.stu.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * 说明
 * 1. 我们自定义一个Handler需要继续netty规定好某个HandlerAdapter（规范）
 * 2. 这是我们自定义一个Handler，才能称为一个handler
 * @author yupeng.zhang
 * @date 2024/1/8
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    //读取数据实际（这里我们可以读取客户端发送的消息）
    /**
     * 1.ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道chanel，地址
     * 2.Object msg：就是客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程" + Thread.currentThread().getName() + " channel = " + ctx.channel());
        System.out.println("server ctx=" + ctx);
        System.out.println("看看channel 和 pipeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链接，出站入站

        //将msg转成一个ByteBuf
        //ByteBuf是Netty提供的，不是NIO的ByteBuffer。
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + channel.remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFklush 是write + flush
        //将数据写入到缓存，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端喵1", CharsetUtil.UTF_8));
    }

    //处理异常，一半是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
