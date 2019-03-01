package transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by youzhihao on 2018/12/20.
 * 用来调试spring的事务相关源码
 */
@EnableAutoConfiguration(exclude = AopAutoConfiguration.class)
@ComponentScan(basePackages = "transaction.*")
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ImportResource({"classpath:datasource.xml"})
@PropertySources(value = {@PropertySource("properties/datasource.properties"),@PropertySource("properties/datasource1.properties")})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
