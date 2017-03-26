package observer;

/**
 * Created by youzhihao on 2017/3/26.
 * 抽象一个观察者
 */
public interface Observer {

    //接收通知
    void receiveNotify(String subjectName, Event event);

}
