package org.stu.netty.nio.buffer.case01;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author yupeng.zhang
 * @date 2024/1/19
 */
public class BufferMain {
    public static void main(String[] args) {
        //1.分配缓冲区
        ByteBuffer bbr = ByteBuffer.allocate(1024);
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=0 lim=1024 cap=1024]

        //2.利用put方法放置参数
        bbr.put("abc".getBytes(StandardCharsets.UTF_8));
        print(bbr);//abc
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=3 lim=1024 cap=1024]

        //3.put索引方式会替换索引上的值，position不变
        bbr.put(0, (byte) 'z');
        print(bbr);//zbc
        bbr.put(1, (byte) 'x');
        print(bbr);//zxc
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=3 lim=1024 cap=1024]

        //4.切换读模式
        bbr.flip();
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=0 lim=3 cap=1024]

        //5.get()读数据，position+1
        for (int i = 0; i < bbr.limit(); i++) {
            System.out.println(bbr.get());//122，120，99
        }
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=3 lim=3 cap=1024]

        //6.get(0)读数据，position不变
        bbr.get(1);
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=3 lim=3 cap=1024]

        //7.重读模式，position，limit还原
        bbr.rewind();
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=0 lim=3 cap=1024]

        //8.读一个字节数组
        byte[] bytes = new byte[bbr.limit()];
        bbr.get(bytes);
        System.out.println(new String(bytes));//zxc
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=3 lim=3 cap=1024]

        //9.buffer是否有剩余未读
        if (bbr.hasRemaining()) {
            System.out.println(bbr.remaining());
        }

        //10.buf.duplicate 共享缓冲区内容
        ByteBuffer bbr1 = bbr.duplicate();

        //11.mark标记当前位置，reset和还原标记位置
        bbr.mark();
        bbr.reset();

        //12.compact保留未读的部分切换到时写模式
        bbr.position(1).limit(3);
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=1 lim=3 cap=1024]
        bbr.compact();
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=2 lim=1024 cap=1024]

        //13.清空并切换到时写模式
        bbr.clear();
        System.out.println(bbr);//java.nio.HeapByteBuffer[pos=0 lim=1024 cap=1024]
    }

    public static void print(ByteBuffer byteBuffer) {
        System.out.println(new String(byteBuffer.array()));
    }
}
