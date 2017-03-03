package factoryMethod;

/**
 * Created by youzhihao on 2017/3/3.
 */
public class SubtractAlgorithmFactory implements AlgorithmFactory {

    @Override
    public Algorithm getObject() {
        return new SubtractAlgorithm();
    }
}
