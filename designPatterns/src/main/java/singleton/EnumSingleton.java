package singleton;

/**
 * Created by youzhihao on 2017/11/23.
 * 利用单元素枚举实现单例
 */
public enum EnumSingleton {

    single;

    //单例的相关方法
    public void method() {
        System.out.println("method");
    }

}
