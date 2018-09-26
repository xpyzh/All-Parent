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
public class TestController {

    @Autowired
    private MemcachedClient memcachedClient;


    @RequestMapping("/dynamicMemcached")
    public String dynamicMemcacheTest() {
        return JSONObject.toJSONString(memcachedClient.getStats());
    }


}
