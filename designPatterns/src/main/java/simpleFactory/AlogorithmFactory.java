package simpleFactory;

/**
 * Created by youzhihao on 2017/3/4.
 */
public class AlogorithmFactory {

    public static Algorithm getObject(String type) {
        Algorithm algorithm;
        switch (type) {
            case "+":
                algorithm = new AddAlgorithm();
                break;
            case "-":
                algorithm = new SubtractAlgorithm();
                break;
            default:
                algorithm = new AddAlgorithm();
                break;
        }
        return algorithm;

    }

}
