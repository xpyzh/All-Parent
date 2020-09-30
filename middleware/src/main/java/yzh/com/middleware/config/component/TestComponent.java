package yzh.com.middleware.config.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yzh.com.middleware.config.service.TestService1;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: 用来测试@ompnentScan扫描的bean，其内部依赖如果没有注入，是否影响@Configuration的加载顺序<br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/25 1:32 下午 <br/>
 * @Author: youzhihao
 */
@Component
public class TestComponent {

    public TestComponent() {
        System.out.println("初始化TestComponent");
    }

    @Autowired
    private TestService1 testService;

}
