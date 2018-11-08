//import com.sun.btrace.BTraceUtils;
//import com.sun.btrace.annotations.BTrace;
//import com.sun.btrace.annotations.Duration;
//import com.sun.btrace.annotations.Kind;
//import com.sun.btrace.annotations.Location;
//import com.sun.btrace.annotations.OnMethod;
//
//import static com.sun.btrace.BTraceUtils.println;
//
///**
// * Created by youzhihao on 2018/11/7.
// * 抓取spanEvent的值
// */
//@BTrace
//public class PinpointCollectorTracer {
//
//    @OnMethod(clazz = "org.apache.rocketmq.store.CommitLog", method = "handleDiskFlush", location = @Location(value = Kind.RETURN))
//    public static void handleDiskFlush(@Duration long duration) throws Exception {
//        String currentTime = BTraceUtils.timestamp("yyyy-MM-dd HH:mm:ss");
//        long elapse = duration / 1000000;
//        if (elapse > 100) {
//            println(currentTime + " handleDiskFlush cost " + elapse + "ms");
//        }
//
//    }
