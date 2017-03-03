package factoryMethod;

/**
 * Created by youzhihao on 2017/3/3.
 */
public class AddAlgorithmFactory implements AlgorithmFactory {

    @Override
    public Algorithm getObject() {
        return new AddAlgorithm();
    }
}
