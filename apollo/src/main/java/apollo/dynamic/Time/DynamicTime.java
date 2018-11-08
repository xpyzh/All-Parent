package apollo.dynamic.Time;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by youzhihao on 2018/10/15.
 * 使用aspectJ织入,修改system.currentTimeMillis实现 只有推送时才生效
 */
@Aspect
@Component
@EnableApolloConfig("application")
public class DynamicTime {

    private static final Logger logger = LoggerFactory.getLogger("dynamic-time");

    private static Object DYNAMIC_TIME_MILLIS = null;

    @Around("call(public static native long java.lang.System.currentTimeMillis())")
    public Object currentTimeMillis(ProceedingJoinPoint pjp) {
        Object time = null;
        try {
            if (DYNAMIC_TIME_MILLIS == null) {
                time = pjp.proceed();
            } else {
                time = DYNAMIC_TIME_MILLIS;
            }
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
        }
        return time;
    }

    @ApolloConfigChangeListener("application")
    public void onChange(ConfigChangeEvent changeEvent) {
        if (changeEvent.isChanged("dynamic.time.millis")) {
            try {
                String oldTimeMillis = changeEvent.getChange("dynamic.time.millis").getOldValue();
                String newTimeMillis = changeEvent.getChange("dynamic.time.millis").getNewValue();
                DYNAMIC_TIME_MILLIS = Long.valueOf(newTimeMillis);
                logger.info("change dynamic time success oldAddrs={},newAddrs={}", oldTimeMillis, newTimeMillis);
            } catch (Throwable e) {
                logger.error("change dynamic time fail", e);
            }

        }
    }


}
