package org.stu.netty.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author yupeng.zhang
 * @date 2024/1/8
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        //创建bytebuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);
        //常用方法
        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            //将content转成字符串
            System.out.println(new String(content, StandardCharsets.UTF_8));

            System.out.println("byteBuf=" + byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            //System.out.println(byteBuf.readByte());
            System.out.println(byteBuf.getByte(0));

            int len = byteBuf.readableBytes();//可读字节数
            System.out.println("len=" + len);

            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            //按照某个范围读取
            System.out.println(byteBuf.getCharSequence(0,4,StandardCharsets.UTF_8));
            System.out.println(byteBuf.getCharSequence(4,6,StandardCharsets.UTF_8));
        }
    }
}
