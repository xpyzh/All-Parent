package apollo.dynamic;

import com.alibaba.fastjson.JSONObject;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youzhihao on 2018/9/26.
 */
@RestController
@RequestMapping("/dynamicMemcached")
public class DynamicMemcachedTest {

    @Autowired
    private MemcachedClient memcachedClient;


    @RequestMapping("/getStats")
    public String getStats() {
        return JSONObject.toJSONString(memcachedClient.getStats());
    }

    @RequestMapping("/get")
    public String get(String key) {
        return JSONObject.toJSONString(memcachedClient.get(key));
    }
    @RequestMapping("/set")
    public String set(String key, String value) {
        return JSONObject.toJSONString(memcachedClient.set(key, value,1000000));
    }


}
