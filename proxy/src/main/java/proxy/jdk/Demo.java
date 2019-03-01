package proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * Created by youzhihao on 2019/1/31.
 * 主要目的:
 * 1.jdk生成动态代理的demo
 * 2.阅读和调试jdk动态代理的源码
 */
public class Demo {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{UserService.class}, new UserInvocationHandler(userService));
        ((UserService) proxy).show();
    }
}
