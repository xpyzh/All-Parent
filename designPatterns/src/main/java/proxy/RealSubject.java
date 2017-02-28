package proxy;

/**
 * Created by youzhihao on 2017/2/28.
 */
public class RealSubject implements Subject {

    @Override
    public void method() {
        System.out.println("真实的方法");
    }
}
