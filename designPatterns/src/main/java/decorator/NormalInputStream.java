package decorator;

/**
 * Created by youzhihao on 2017/10/19.
 */
public class NormalInputStream implements InputStream {

    @Override
    public void read() {
        System.out.println("普通的read");
    }
}
