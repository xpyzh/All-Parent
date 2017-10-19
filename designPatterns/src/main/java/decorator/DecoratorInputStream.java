package decorator;

/**
 * Created by youzhihao on 2017/10/19.
 */
public class DecoratorInputStream implements InputStream {

    private InputStream inputStream;

    public DecoratorInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void read() {
        if (inputStream != null) {
            inputStream.read();
        }
    }
}
