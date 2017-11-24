package singleton;

/**
 * Created by youzhihao on 2017/11/23.
 * 双重检查
 */
public class DoubleCheckSingleton {

    private static volatile DoubleCheckSingleton singleton;


    private DoubleCheckSingleton() {
        System.out.println("创建:DoubleCheckSingleton");
    }


    public static DoubleCheckSingleton getInstance() {
        if (singleton == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (singleton == null) {
                    singleton = new DoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }

}
