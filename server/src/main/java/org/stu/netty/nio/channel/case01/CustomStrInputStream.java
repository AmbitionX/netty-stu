package org.stu.netty.nio.channel.case01;

import java.io.*;

/**
 * @author yupeng.zhang
 * @date 2024/1/19
 */
public class CustomStrInputStream extends FileInputStream {

    public CustomStrInputStream(File file) throws FileNotFoundException {
        super(file);
    }
}
