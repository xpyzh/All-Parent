package yzh.com.middleware.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.text.MessageFormat;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/23 1:19 下午 <br/>
 * @Author: youzhihao
 */
@Configuration
//指定MiddlewareProperties会根据属性自动生成一个单例的bean出来
@EnableConfigurationProperties(MiddlewareProperties.class)
//加载额外的配置文件
@PropertySource(value = {"classpath:application1.yml"})
public class MiddleWareAutoConfiguration implements EnvironmentAware {

    private ConfigurableEnvironment env;

    @Autowired
    private MiddlewareProperties middlewareProperties;

    @Bean
    public void test() {
        System.out.println(middlewareProperties);
    }

    /**
     * DestructionAwareBeanPostProcessor这个处理器比较特殊，会影响ConfigurationProperties的注入，
     * 因此需要放在内部静态类里面单独加载,见BeforeConfiguration
     */

//    @Bean
//    public LifecycleBeanPostProcessor addLifecycleBeanPostProcessor() {
//        System.out.println("初始化DestructionAwareBeanPostProcessor");
//        return new LifecycleBeanPostProcessor();
//    }

    //环境和配置相关钩子
    @Override
    public void setEnvironment(Environment environment) {
        Assert.isInstanceOf(ConfigurableEnvironment.class, environment);
        this.env = (ConfigurableEnvironment) environment;
        //获取PropertySources
        env.getPropertySources();
        //获取Property
        System.out.println(MessageFormat.format("appCode={0}", env.getProperty("appCode")));

    }

    /**
     *
     */
    @Configuration
    public static class BeforeConfiguration {

        @Bean
        public LifecycleBeanPostProcessor addLifecycleBeanPostProcessor() {
            System.out.println("初始化DestructionAwareBeanPostProcessor");
            return new LifecycleBeanPostProcessor();
        }
    }
}
