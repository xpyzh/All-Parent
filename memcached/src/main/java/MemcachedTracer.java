import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

import static com.sun.btrace.BTraceUtils.println;

/**
 * Created by youzhihao on 2018/9/27.
 */
@BTrace
public class MemcachedTracer {


//    @OnMethod(clazz = "net.spy.memcached.MemcachedClientInner", method = "add",
//            type = "boolean(java.lang.String,java.lang.Object,java.lang.Long,java.lang.Integer)", location = @Location(value = Kind.RETURN))
//    public static void addMonitor(String key, Object value, Long exp, Integer hashCode) throws Exception {
//        long expire_time = BTraceUtils.timeMillis() + exp;
//        println(key + "," + expire_time + "," + exp + "," + hashCode);
//    }
//
//    @OnMethod(clazz = "net.spy.memcached.MemcachedClientInner", method = "set",
//            type = "boolean(java.lang.String,java.lang.Object,java.lang.Long,java.lang.Integer)", location = @Location(value = Kind.RETURN))
//    public static void setMonitor(String key, Object value, Long expiry, Integer hashCode) throws Exception {
//        long expire_time = BTraceUtils.timeMillis() + expiry;
//        println(key + "," + expire_time + "," + expiry + "," + hashCode);
//    }

    @OnMethod(clazz = "net.spy.memcached.MemcachedClientInner", method = "get",
            type = "boolean(java.lang.String,java.lang.Integer,boolean)", location = @Location(value = Kind.RETURN))
    public static void getMonitor(String key,Integer hashCode,boolean asString) throws Exception {
        println(key);
    }

}
