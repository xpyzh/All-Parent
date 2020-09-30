package yzh.com.middleware.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/30 1:49 下午 <br/>
 * @Author: youzhihao
 */
public class MiddlewareBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
