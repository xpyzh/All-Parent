package transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by youzhihao on 2018/12/20.
 * 用来调试spring的事务相关源码
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "transaction.*")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@ImportResource({"classpath:datasource.xml"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
