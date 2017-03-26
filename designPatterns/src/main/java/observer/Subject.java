package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youzhihao on 2017/3/26.
 * 抽象一个主题
 * eg.
 * 这里没有考虑线程安全的问题，仅仅是基于设计模式的角度上进行demo梳理
 * 集合的选择，和添加，删除和通知方法具体在多线程并发情况下的实现，还需要仔细斟酌
 */
public abstract class Subject {

    //添加一个观察者
    private List<Observer> observerList = new ArrayList<>();

    private String subjectName;

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    //删除一个观察者
    public void deleteObserver(Observer observer) {
        observerList.add(observer);
    }

    //发送通知给所有添加的观察者
    public void sendNotify(Event event) {
        for (Observer observer : observerList) {
            observer.receiveNotify(this.subjectName,event);
        }
    }

}
