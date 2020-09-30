package yzh.com.middleware.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: 对象注入配置<br />
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/23 2:07 下午 <br/>
 * @Author: youzhihao
 */
//表明该对象会绑定指定前缀的属性
@ConfigurationProperties(prefix = MiddlewareProperties.PREFIX)
@Data
public class MiddlewareProperties {

    //定义前缀P
    public static final String PREFIX = "middleware";

    //普通字符串
    private String appCode;

    //数组字符串
    private String[] configPath;

    //嵌套对象
    private Map<String, MiddlewareProperty> middlewareProperties = new LinkedHashMap<>();


}
