package singleton;

/**
 * Created by youzhihao on 2017/11/23.
 * 饿汉模式
 */
public class EagerSingleton {

    private static EagerSingleton singleton = new EagerSingleton();

    private EagerSingleton() {
        System.out.println("创建:EagerSingleton");
    }

    public static EagerSingleton getInstance() {
        return singleton;
    }

}
