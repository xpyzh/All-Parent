package apollo.demo;

/**
 * Created by youzhihao on 2018/9/26.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * debug apollo的客户端端专用模块
 * -Dapp.id=apollo-demo -Dspring.profiles.active=dev -Dapollo.meta=http://10.216.40.201:8080
 * @author youzhihao
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "apollo.demo")
@EnableAspectJAutoProxy(proxyTargetClass = false)
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
