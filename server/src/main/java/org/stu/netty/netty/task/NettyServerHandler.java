package org.stu.netty.netty.task;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author yupeng.zhang
 * @date 2024/1/8
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据实际（这里可以读取客户端发送的消息）

    /**
     * 1.ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道channel，地址
     * 2.Object msg：客户端发送的数据，默认Object
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //比如这里有个非常耗时的业务-->异步执行-》提交该channel对应的NIOEventLoog的taskQueue中，
        //方案一 用户程序自定义的普通任务
        ctx.channel().eventLoop().execute(()->{
            try {
                Thread.sleep(5*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端喵2", CharsetUtil.UTF_8));
                System.out.println("channel code="+ctx.channel().hashCode());
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.printf("发生异常 %s", e.getMessage());
            }
        });

        ctx.channel().eventLoop().execute(()->{
            try {
                Thread.sleep(5*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端喵3", CharsetUtil.UTF_8));
                System.out.println("channel code="+ctx.channel().hashCode());
            } catch (InterruptedException e) {
                System.out.printf("发生异常 %s", e.getMessage());
                e.printStackTrace();
            }
        });

        //解决方案2： 用户自定义定时任务-》该任务提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(()->{
            try {
                Thread.sleep(5 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端喵4", CharsetUtil.UTF_8));
                System.out.printf("channel code=%s",ctx.channel().hashCode());
            } catch (InterruptedException e) {
                System.out.printf("发生异常 %s",e.getMessage());
            }
        },5, TimeUnit.SECONDS);
        System.out.println("go on ...");
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush是write+flush
        //将数据写入到缓存并刷新
        //一般将，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端喵1", CharsetUtil.UTF_8));
    }

    //处理异常，一般是关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
