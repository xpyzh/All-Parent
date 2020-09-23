package yzh.com.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class MiddleWareAutoConfiguration {

    @Autowired
    private MiddlewareProperties middlewareProperties;

    @Bean
    public void test() {
     System.out.println(middlewareProperties);
    }



}
