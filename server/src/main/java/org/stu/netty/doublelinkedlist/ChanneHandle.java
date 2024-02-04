package org.stu.netty.doublelinkedlist;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

public interface ChanneHandle {

    void handlerAdded(ChannelHandlerContext ctx) throws Exception;
    void handlerRemoved(ChannelHandlerContext ctx) throws Exception;
}
