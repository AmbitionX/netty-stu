package org.stu.netty.netty.demo.case1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author yupeng.zhang
 * @date 2024/1/8
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        //假如一个netty提供的httpServerCodec codec=》【coder - decoder】
        //HttpServerCodec说明
        //1. HttpServerCodec是netty提供处理http的编-解码器
        pipeline.addLast("MyhttpServerCodec", new HttpServerCodec());
        //2. 增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());

        System.out.println("ok~~~~~");
    }
}
