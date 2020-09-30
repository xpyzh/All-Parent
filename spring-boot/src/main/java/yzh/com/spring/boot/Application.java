package yzh.com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/29 4:26 下午 <br/>
 * @Author: youzhihao
 */
@SpringBootApplication
@ImportResource("classpath*:dubbo.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
