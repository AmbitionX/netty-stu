package org.stu.netty.netty.protobuf;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.Arrays;

/**
 * @author yupeng.zhang
 * @date 2024/1/9
 */
public class Main {

    public static void main(String[] args) {
        FileOuterClass.File.Builder builder = FileOuterClass.File.newBuilder();
        FileOuterClass.File proto = builder.setName("proto").setSize(12).build();

        byte[] s = proto.toByteArray();
        System.out.println("protobuf数据大小bytes[]：" + Arrays.toString(s));
        System.out.println("protobuf序列化大小：" + s.length);

        FileOuterClass.File file1 = null;
        String jsonObject = null;
        try {
            file1 = FileOuterClass.File.parseFrom(s);
            jsonObject = JsonFormat.printer().print(file1);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.println("Json格式化结果:\n" + jsonObject);
        System.out.println("Json格式化数据大小: " + jsonObject.getBytes().length);
    }
}
