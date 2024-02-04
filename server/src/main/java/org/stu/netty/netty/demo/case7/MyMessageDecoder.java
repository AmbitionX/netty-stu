package org.stu.netty.netty.demo.case7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author yupeng.zhang
 * @date 2024/1/10
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MymessageDecoder decode 方法被调用");
        //需要得到二进制字节码-》MessageProtocol数据包对象
        int length = in.readInt();

        byte[] content = new byte[length];
        in.readBytes(content);

        //封装成MessageProtocol对象，放入out，传入下一个业务处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);
        out.add(messageProtocol);
    }
}
