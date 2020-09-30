package yzh.com.middleware.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yzh.com.middleware.config.service.TestService2;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: 用来测试Configuration的加载顺序<br />
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/25 1:22 下午 <br/>
 * @Author: youzhihao
 */
@Configuration
@AutoConfigureBefore(MiddleWareAutoConfiguration1.class)
public class MiddleWareAutoConfiguration2 {

    @Autowired
    private MiddlewareProperties middlewareProperties;

    public MiddleWareAutoConfiguration2() {
        System.out.println("初始化MiddleWareAutoConfiguration2");
    }

    @Bean
    @ConditionalOnMissingBean
    public TestService2 initTestService2() {
        System.out.println("初始化TestService2");
        return new TestService2();
    }
}
