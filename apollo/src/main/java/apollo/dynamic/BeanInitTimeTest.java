package apollo.dynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apollo.dynamic.component.TimePostProcessor;

/**
 * Created by youzhihao on 2018/10/27.
 */
@RestController
public class BeanInitTimeTest {
    @Autowired
    private TimePostProcessor timePostProcessor;

    @RequestMapping("/time")
    public String time() {
        timePostProcessor.initializationTime();
        return "";
    }
}
