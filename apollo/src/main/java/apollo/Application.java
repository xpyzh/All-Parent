package apollo;

/**
 * Created by youzhihao on 2018/9/26.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 项目启动类
 * -Dspring.profiles.active=dev
 * @author youzhihao
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "apollo")
@ImportResource(value = "classpath:config/dynamic_application.xml")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
