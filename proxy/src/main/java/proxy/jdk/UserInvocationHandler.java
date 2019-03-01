package proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by youzhihao on 2019/1/31.
 */
public class UserInvocationHandler implements InvocationHandler {
    private final Object target;

    public UserInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("UserInvocationHandler.before");
        Object result = method.invoke(target, args);
        System.out.println("UserInvocationHandler.after");
        return result;
    }
}
