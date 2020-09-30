package yzh.com.middleware.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yzh.com.middleware.config.service.TestService1;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: 用来测试Configuration的加载顺序<br />
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/25 1:22 下午 <br/>
 * @Author: youzhihao
 */
@Configuration
@AutoConfigureBefore(MiddleWareAutoConfiguration.class)
public class MiddleWareAutoConfiguration1 {

    @Autowired
    private MiddlewareProperties middlewareProperties;

    @Autowired
    private TestService1 testService1;

    public MiddleWareAutoConfiguration1() {
        System.out.println("初始化MiddleWareAutoConfiguration1");
    }


    /**
     * BeanPostProcessor会优先实例化出来，不受autoConfiguration的顺序影响
     */
    @Bean
    public MiddlewareBeanPostProcessor middlewareBeanPostProcessor() {
        System.out.println("初始化middlewareBeanPostProcessor");
        return new MiddlewareBeanPostProcessor();
    }
    @Bean
    public void test1()
    {
        System.out.println(testService1);
    }

    @Bean
    @ConditionalOnMissingBean
    public TestService1 initTestService1() {
        System.out.println("初始化TestService1");
        TestService1 testService1=new TestService1();
        return testService1;
    }

}
