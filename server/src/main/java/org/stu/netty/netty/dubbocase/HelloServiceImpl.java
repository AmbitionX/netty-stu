package org.stu.netty.netty.dubbocase;

/**
 * @author yupeng.zhang
 * @date 2024/1/30
 */
public class HelloServiceImpl implements HelloService {

    private static int count = 0;

    /**
     * 有调用者调用该方法，就返回一个结果
     * @param mes
     * @return
     */
    @Override
    public String hello(String mes) {
        System.out.println("收到客户端信息="+mes);
        //根据mes不同，返回不同结果
        if (mes != null) {
            return "你好客户端，我已收到你的信息 [" + mes + "] 第" + (++count) + " 次";
        } else {
            return "你好客户端，我已收到你的信息 ";
        }
    }
}
