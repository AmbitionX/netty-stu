package org.stu.netty.netty.dubbocase;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author yupeng.zhang
 * @date 2024/1/31
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;//上下文
    private String result;//返回的结果
    private String para;//客户端调用方法 传入的参数


    /**
     * 与服务器创建连接后，就会被调用，这个是第一个方法被调用（1）
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive被调用  ");
        context = ctx;
    }

    /**
     * 收到服务器的创建后
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" channelRead 被调用  ");
        result = msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    /**
     * 被代理对象调用，发送数据给服务器，-》wait-》等待被唤醒（channelRead）-》返回结果（3）-》5
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call1 被调用");
        context.writeAndFlush(para);
        //进行wait
        wait();//等待channelRead方法获取到服务器的结果后，唤醒
        System.out.println("call2 被调用");
        return result;
    }

    void setPara(String para) {
        System.out.println("  setPara  ");
        this.para = para;
    }
}
