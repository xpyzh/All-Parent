package simpleFactory;

/**
 * Created by youzhihao on 2017/3/3.
 * 简单工厂，违背了开放封闭原则，但是开发较为简单
 */
public class Run {

    public static void main(String[] args) {
        //创建一个加法算法，并计算
        Algorithm addAlgorithm = AlogorithmFactory.getObject("+");
        System.out.println(addAlgorithm.compute(10, 5));
        //创建一个减法算法，并计算
        Algorithm subAlgorithm = AlogorithmFactory.getObject("-");
        System.out.println(subAlgorithm.compute(10, 5));
    }

}
