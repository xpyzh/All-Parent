package transaction.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * Created by youzhihao on 2019/1/22.
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equalsIgnoreCase("&user") || beanName.equalsIgnoreCase("user")) {
            System.out.println(MessageFormat.format("CustomBeanPostProcessor.postProcessBeforeInitialization,beanName={0},bean={1}", beanName, bean.getClass().toString()));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equalsIgnoreCase("&user") || beanName.equalsIgnoreCase("user")) {
            System.out.println(MessageFormat.format("CustomBeanPostProcessor.postProcessAfterInitialization,beanName={0},bean={1}", beanName, bean.getClass().toString()));
        }
        return bean;
    }


}
