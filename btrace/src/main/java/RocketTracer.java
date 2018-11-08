import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Duration;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

import static com.sun.btrace.BTraceUtils.println;

/**
 * Created by youzhihao on 2018/8/29.
 */
@BTrace
public class RocketTracer {
    @OnMethod(clazz = "org.apache.rocketmq.store.CommitLog", method = "handleDiskFlush", location = @Location(value = Kind.RETURN))
    public static void handleDiskFlush(@Duration long duration) throws Exception {
        String currentTime = BTraceUtils.timestamp("yyyy-MM-dd HH:mm:ss");
        long elapse = duration / 1000000;
        if (elapse > 100) {
            println(currentTime + " handleDiskFlush cost " + elapse + "ms");
        }

    }

    @OnMethod(clazz = "org.apache.rocketmq.store.CommitLog", method = "handleHA", location = @Location(value = Kind.RETURN))
    public static void handleHA(@Duration long duration) throws Exception {
        String currentTime = BTraceUtils.timestamp("yyyy-MM-dd HH:mm:ss");
        long elapse = duration / 1000000;
        if (elapse > 100) {
            println(currentTime + " handleHA cost " + elapse + "ms");
        }
    }

    @OnMethod(clazz = "org.apache.rocketmq.store.CommitLog", method = "putMessage", location = @Location(value = Kind.RETURN))
    public static void putMessage(@Duration long duration) throws Exception {
        String currentTime = BTraceUtils.timestamp("yyyy-MM-dd HH:mm:ss");
        long elapse = duration / 1000000;
        if (elapse > 100) {
            println(currentTime + " putMessage cost " + elapse + "ms");
        }
    }
}
