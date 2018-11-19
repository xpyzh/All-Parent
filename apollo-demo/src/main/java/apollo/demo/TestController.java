package apollo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

/**
 * Created by youzhihao on 2018/11/16.
 */
@RestController
public class TestController {

    @Autowired
    private BaseConfig baseConfig;

    @RequestMapping("/configTest")
    public String configTest() {
        return MessageFormat.format("normalField={0},staticField={1}", baseConfig.getNormalField(), BaseConfig.getStaticField());
    }
}
