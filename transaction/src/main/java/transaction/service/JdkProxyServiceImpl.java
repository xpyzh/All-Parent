package transaction.service;

import org.springframework.stereotype.Service;

/**
 * Created by youzhihao on 2019/1/23.
 */
@Service
public class JdkProxyServiceImpl implements JdkProxyService {
    @Override
    public void test() {
        System.out.println("JdkProxyServiceImpl.test");
    }
}
