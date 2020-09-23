package yzh.com.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/23 2:07 下午 <br/>
 * @Author: youzhihao
 */
//表明该对象会绑定指定前缀的属性
@ConfigurationProperties(prefix = MiddlewareProperties.PREFIX)
@Data
public class MiddlewareProperties {

    //定义前缀
    public static final String PREFIX = "middleware";

    private String appCode;


}
