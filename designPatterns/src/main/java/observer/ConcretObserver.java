package observer;

import java.text.MessageFormat;

/**
 * Created by youzhihao on 2017/3/26.
 */
public class ConcretObserver implements Observer {

    private String observerName;


    public ConcretObserver(String observerName) {
        this.observerName = observerName;
    }

    @Override
    public void receiveNotify(String subjectName, Event event) {
        System.out.println(MessageFormat.format("订阅者:{0},订阅的主题:{1},收到消息:{2}",
                observerName, subjectName, event.getContent()));
    }
}
