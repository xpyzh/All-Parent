package decorator;

import java.text.MessageFormat;

/**
 * Created by youzhihao on 2017/2/27.
 */
public class Person implements Cloth {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public void wear() {
        System.out.println(MessageFormat.format("{0}穿衣搭配如下:", name));
    }
}
