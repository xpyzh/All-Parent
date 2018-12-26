package apollo.demo;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by youzhihao on 2018/11/16.
 */
@Component
@EnableApolloConfig
public class BaseConfig {
    static {
        System.setProperty("normal.field", "System.setProperty");

    }

    @Value("${normal.field}")
    private String normalField;

    @Value("${static.field}")
    private static String staticField;

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        System.out.println(changeEvent.changedKeys());

    }

    public String getNormalField() {
        return normalField;
    }

    public void setNormalField(String normalField) {
        this.normalField = normalField;
    }

    public static String getStaticField() {
        return staticField;
    }

    public static void setStaticField(String staticField) {
        BaseConfig.staticField = staticField;
    }
}
