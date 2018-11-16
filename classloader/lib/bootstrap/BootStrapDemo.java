import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by youzhihao on 2018/11/11.
 * BootStrapDemo通过启动类加载器(Bootstrap ClassLoader)指定-XbootClasspath来加载，模拟基础类调用com.mysql.jdbc的实现类
 * 模拟在bootStrap
 */
public class BootStrapDemo {

    //DriverManager.getConnection()使用线程上下文类加载器破坏了双亲模式
    public static Connection getConnection() throws Exception {
        System.out.println("BootStrapDemo的classloader为:" + BootStrapDemo.class.getClassLoader());
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1/cg_yanxuan?useUnicode=true&characterEncoding=utf-8", "root", "root");
    }
}
