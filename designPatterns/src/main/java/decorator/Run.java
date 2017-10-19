package decorator;

/**
 * Created by youzhihao on 2017/10/19.
 */
public class Run {

    public static void main(String[] args) {
        System.out.println("组合1:");
        ByteBufferInputStream inputStream1 = new ByteBufferInputStream(new NormalInputStream());
        inputStream1.read();
        System.out.println("组合2:");
        ConcurrentInputStream inputStream2 = new ConcurrentInputStream(new NormalInputStream());
        inputStream2.read();
        System.out.println("组合3:");
        ConcurrentInputStream inputStream3 = new ConcurrentInputStream(new ByteBufferInputStream(new NormalInputStream()));
        inputStream3.read();
    }
}
