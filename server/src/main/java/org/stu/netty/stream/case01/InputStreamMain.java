package org.stu.netty.stream.case01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * @author yupeng.zhang
 * @date 2024/1/22
 */
public class InputStreamMain {

    public static void main(String[] args) throws Exception{
        BufferedReader bufferedReader = new CustomReader(new BufferedReader(new BufferedReader(new FileReader("D:\\project\\practice\\netty-stu\\server\\src\\main\\java\\org\\stu\\netty\\stream\\case01\\test.txt"))));
        String str = bufferedReader.readLine();
        while (str != null) {
            System.out.println(str);
            str = bufferedReader.readLine();
        }

    }

}
