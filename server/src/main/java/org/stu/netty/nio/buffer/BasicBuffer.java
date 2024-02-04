package org.stu.netty.nio.buffer;

import java.nio.IntBuffer;

/**
 * @author yupeng.zhang
 * @date 2024/1/2
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //buffer使用例子
        //创建一个buffer，大小为5，可以存放5个int
        IntBuffer intBuffer  = IntBuffer.allocate(5);
        //像buffer存放数据
        //intBuffer.put(10);
        //intBuffer.put(11);
        //intBuffer.put(12);
        //intBuffer.put(13);
        //intBuffer.put(14);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        //如何从buffer读，将buffer装换，读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
