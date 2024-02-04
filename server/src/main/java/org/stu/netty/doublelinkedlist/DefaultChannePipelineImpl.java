package org.stu.netty.doublelinkedlist;

import io.netty.channel.ChannelPipeline;

/**
 * @author yupeng.zhang
 * @date 2024/1/11
 */
public class DefaultChannePipelineImpl implements ChannePipeline {



    @Override
    public ChannelPipeline addFirst(String name, ChanneHandle handler) {
        return null;
    }

    @Override
    public ChannelPipeline addLast(String name, ChanneHandle handler) {
        return null;
    }

    @Override
    public ChanneHandle remove(String name) {
        return null;
    }

    @Override
    public ChanneHandle removeFirst() {
        return null;
    }

    @Override
    public ChanneHandle removeLast() {
        return null;
    }
}
