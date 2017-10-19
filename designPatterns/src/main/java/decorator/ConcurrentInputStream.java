package decorator;

/**
 * Created by youzhihao on 2017/10/19.
 */
public class ConcurrentInputStream extends DecoratorInputStream {

    public ConcurrentInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public void read() {
        System.out.println("增强功能:并发读取");
        super.read();
        System.out.println("增强功能:并发读取");

    }
}
