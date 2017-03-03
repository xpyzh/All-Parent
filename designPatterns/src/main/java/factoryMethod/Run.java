package factoryMethod;

/**
 * Created by youzhihao on 2017/3/3.
 * 工厂方法模式：
 * 和简单工厂模式对比，在工程类中消除了判定创建具体实例的逻辑。
 * 简单工厂违背了：开放-封闭原则
 * 工厂方法模式遵守了：开放-封闭原则
 */
public class Run {

    public static void main(String[] args) {
        //创建一个加法算法，并计算
        AlgorithmFactory addFactory = new AddAlgorithmFactory();
        Algorithm addAlgorithm = addFactory.getObject();
        System.out.println(addAlgorithm.compute(10, 5));
        //创建一个减法算法，并计算
        AlgorithmFactory subFactory = new SubtractAlgorithmFactory();
        Algorithm subAlgorithm = subFactory.getObject();
        System.out.println(subAlgorithm.compute(10, 5));
        //如果后面还需要增加乘法算法，不需要更改原有的任何代码，只需要增加一个乘法算法和对应的乘法工厂
    }

}
