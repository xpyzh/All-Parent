package proxy;

/**
 * Created by youzhihao on 2017/2/28.
 */
public class Proxy implements Subject {

    private RealSubject realSubject;

    public Proxy(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void method() {
        realSubject.method();

    }
}
