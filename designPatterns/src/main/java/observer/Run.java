package observer;

/**
 * Created by youzhihao on 2017/3/26.
 * 观察者模式的demo
 */
public class Run {

    public static void main(String[] args) {
        //创建主题
        NickSubject subject = new NickSubject("耐克店折扣");
        //添加观察者
        ConcretObserver observer1 = new ConcretObserver("小明");
        ConcretObserver observer2 = new ConcretObserver("小王");
        subject.addObserver(observer1);
        subject.addObserver(observer2);
        subject.sendNotify(new Event("八折促销"));

    }

}
