package apollo.dynamic.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youzhihao on 2018/10/27.
 * 目的是监控每一个bean的初始化时间，看样子这个不是很准？后面可以从spring内部类做aop着手试试
 */
@Component
public class TimePostProcessor implements BeanPostProcessor, Ordered, SmartLifecycle {

    @Autowired
    private TimeFactoryPostProcessor timeFactoryPostProcessor;

    private Map<String, Long> start;

    private Map<String, Long> end;

    public TimePostProcessor() {
        start = new HashMap<>();
        end = new HashMap<>();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        start.put(beanName, System.currentTimeMillis());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        end.put(beanName, System.currentTimeMillis());
        return bean;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {

    }

    @Override
    public void start() {
        initializationTime();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }

    //this method returns initialization time of the bean.
    public void initializationTime() {
        for (Map.Entry<String, Long> entry : end.entrySet()) {
            System.out.println(MessageFormat.format("beanName={0},time={1}", entry.getKey(), entry.getValue() - timeFactoryPostProcessor.getLaunchTime()));
        }
    }
}
