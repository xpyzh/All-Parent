package apollo.dynamic.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Created by youzhihao on 2018/10/27.
 */

@Component
public class TimeFactoryPostProcessor implements BeanFactoryPostProcessor {

    private long launchTime;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        launchTime = System.currentTimeMillis();
    }

    public long getLaunchTime() {
        return launchTime;
    }
}