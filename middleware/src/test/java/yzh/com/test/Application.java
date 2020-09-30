package yzh.com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * web 入口
 *
 * @author youzhihao
 */
@SpringBootApplication(scanBasePackages = "yzh.com.spring.config")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
