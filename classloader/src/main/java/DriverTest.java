import java.lang.reflect.Method;

/**
 * Created by youzhihao on 2018/11/11.
 * 模拟jndi破坏双亲委派模式的场景，父classLoader的类需要调用子classLoader的实现
 * jndi的破坏双亲委派的实现是在ServiceLoader.load方法，读取meta-info里的类名，然后使用线程上下文类加载器去加载类
 * 调试参数:-Xbootclasspath/a:/Users/youzhihao/IdeaProjects/All-Parent/classloader/lib/bootstrap
 */
public class DriverTest {
    public static void main(String[] args) throws Exception {
        /**
         * 调用流程Bootstrap:
         * 1.Bootstrap ClassLoader加载BootStrapDemo类，调用getConnection方法
         * 2.Bootstrap ClassLoader加载DriverManager类，触发DriverManager类中的静态块
         * 3.静态块中执行ServiceLoader.load(Driver.class)静态方法
         * 4.实例化ServiceLoader()，传入线程上下文类加载器(这里默认是应用类加载器)，构造出一个懒类迭代器LazyIterator
         * 5.LazyIterator调用next方法，随后会在hasNext中查找meta-info目录下的具体实现类名称，然后使用线程上下文类加载器进行类的加载(破坏双亲委派模式),并创建实例
         */
        Method method = DriverTest.class.forName("BootStrapDemo").getDeclaredMethod("getConnection");
        Object conn = method.invoke(DriverTest.class);
        System.out.println(conn);
    }
}
