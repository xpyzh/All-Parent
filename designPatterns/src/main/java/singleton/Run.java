package singleton;


/**
 * Created by youzhihao on 2017/11/23.
 * 提供集中常见的线程安全的单例模式的实现方式
 */
public class Run {

    public static void main(String[] args) {
        testEagerSingleton();
        testDoubleCheckSingleton();
        testHolderSingleton();
        testEnumSingleton();
    }


    public static void testEagerSingleton() {
        EagerSingleton.getInstance();
    }

    public static void testDoubleCheckSingleton() {
        DoubleCheckSingleton.getInstance();
    }

    public static void testHolderSingleton() {
        HolderSingleton.getInstance();
    }
    public static void testEnumSingleton()
    {
        EnumSingleton.single.method();
    }

}
