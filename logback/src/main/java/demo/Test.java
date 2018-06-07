package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by youzhihao on 2018/4/9.
 */
public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        try {
            Integer integer = null;
            integer.intValue();
        } catch (Exception e) {
            logger.debug("logger.debug", e);
            logger.warn("logger.warn", e);
            logger.error("logger.error", e);
        }
    }


}
