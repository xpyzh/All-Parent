package apollo.dynamic.memcache;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by youzhihao on 2018/9/26.
 */
@Component
@ConditionalOnClass(value = ConfigChangeEvent.class)
@EnableApolloConfig("dynamic-memcached")
public class DynamicMemcachedConfig{

    private static final Logger logger = LoggerFactory.getLogger("dynamic-memcached");

    @Autowired
    private ApplicationContext context;


    @ApolloConfigChangeListener("dynamic-memcached")
    public void onChange(ConfigChangeEvent changeEvent) {
        if (changeEvent.isChanged("memcached.addrs")) {
            try {
                DynamicMemcachedClient memcachedClient = context.getBean(DynamicMemcachedClient.class);
                String oldAddrs = changeEvent.getChange("memcached.addrs").getOldValue();
                String newAddrs = changeEvent.getChange("memcached.addrs").getNewValue();
                memcachedClient.setAddrs(newAddrs);
                memcachedClient.afterPropertiesSet();
                logger.info("change memcached ip success! oldAddrs={},newAddrs={}", oldAddrs, newAddrs);

            } catch (Throwable e) {
                logger.error("change memcached ip fail!", e);
            }

        }
    }
}
