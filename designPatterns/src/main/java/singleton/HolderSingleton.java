package singleton;

/**
 * Created by youzhihao on 2017/11/23.
 * 利用静态内部类实现单例
 * 1.利用静态内部类在外部类第一次使用到它的时候才会加载，保证懒加载
 * 2.利用jvm保证相关静态初始化的线程安全，保证线程安全
 */
public class HolderSingleton {

    private HolderSingleton() {
        System.out.println("创建:HolderSingleton");
    }

    public static HolderSingleton getInstance() {
        return Holder.singleton;
    }

    private static class Holder {

        private static HolderSingleton singleton = new HolderSingleton();
    }

}
