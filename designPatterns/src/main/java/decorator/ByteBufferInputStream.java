package decorator;

/**
 * Created by youzhihao on 2017/10/19.
 */
public class ByteBufferInputStream extends DecoratorInputStream {

    public ByteBufferInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public void read() {
        System.out.println("增强功能:管道缓存");
        super.read();
        System.out.println("增强功能:管道缓存");
    }
}
