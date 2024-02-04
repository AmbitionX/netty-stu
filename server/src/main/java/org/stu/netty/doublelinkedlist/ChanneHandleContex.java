package org.stu.netty.doublelinkedlist;

public interface ChanneHandleContex {
    String name();

    ChanneHandle handler();
    ChanneHandleContex fireChannelActive();
    ChanneHandleContex read();
    ChanneHandleContex flush();
}
