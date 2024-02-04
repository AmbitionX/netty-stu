package org.stu.netty.doublelinkedlist;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

import java.util.NoSuchElementException;

/**
 * @author yupeng.zhang
 * @date 2024/1/11
 */
public interface ChannePipeline {

    ChannelPipeline addFirst(String name, ChanneHandle handler);
    ChannelPipeline addLast(String name, ChanneHandle handler);
    ChanneHandle remove(String name);
    ChanneHandle removeFirst();
    ChanneHandle removeLast();
}
