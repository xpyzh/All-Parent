package transaction.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import transaction.po.User;

/**
 * Created by youzhihao on 2019/1/22.
 * 简单的一个User的FactoryBean
 * 主要用来debug，看看FacotryBean获取后的实例，BeanPostProcessor是否会处理
 * 结论:
 * 1.FactoryBean在初始化时，会调用CustomBeanPostProcessor.postProcessBeforeInitialization和CustomBeanPostProcessor.postProcessAfterInitialization
 * 2.FactoryBean的真正需要的bean初始化时，只会调用CustomBeanPostProcessor.postProcessAfterInitialization,详细看FactoryBeanRegistrySupport.getObjectFromFactoryBean()方法
 */
@Component(value = "user")
public class UserFactoryBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return new User("youzhihao");
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
