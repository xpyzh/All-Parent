package factoryMethod;

/**
 * Created by youzhihao on 2017/3/3.
 */
public class AddAlgorithm implements Algorithm {

    @Override
    public double compute(double a, double b) {
        return a + b;
    }
}
