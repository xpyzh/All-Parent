package simpleFactory;


/**
 * Created by youzhihao on 2017/3/3.
 */
public class SubtractAlgorithm implements Algorithm {

    @Override
    public double compute(double a, double b) {
        return a - b;
    }
}
