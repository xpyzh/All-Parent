package yzh.com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: 单元测试基类
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/23 1:25 下午 <br/>
 * @Author: youzhihao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    @Test
    public void testDemo1() {
        System.out.println("111111");
    }
}

