package org.stu.netty.stream.case01;

import io.netty.util.internal.StringUtil;

import java.io.BufferedReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Objects;

/**
 * @author yupeng.zhang
 * @date 2024/1/22
 */
public class CustomReader extends BufferedReader {


    public CustomReader(Reader in) {
        super(in);
    }

    @Override
    public String readLine() throws IOException {
        String str = super.readLine();
        return str != null && str.length() > 0 ? str + " Hell year!!!" : str;
    }
}
