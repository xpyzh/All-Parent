package apollo.dynamic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by youzhihao on 2018/10/15.
 */
@RestController
@RequestMapping("/dynamicTime")
public class DynamicTimeTest {

    @RequestMapping("/test")
    public String test() {
        System.out.println("System.currentTimeMillis():" + System.currentTimeMillis());
        System.out.println("new Date().getTime():" + new Date().getTime());
        return "";
    }

}
