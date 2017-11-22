package memento;

/**
 * Created by youzhihao on 2017/11/22.
 * 1.备忘录模式，也叫做Snapshot或者token模式
 * 2.备忘录对象是一个用来存储另外一个对象内部状态的快照的对象。
 * 备忘录模式的用意是在不破坏封装的条件下，将一个对象的状态捕捉(Capture)住，并外部化，存储起来，
 * 从而可以在将来合适的时候把这个对象还原到存储起来的状态。备忘录模式常常与命令模式和迭代子模式一同使用。
 */
public class Run {

    public static void main(String[] args) {
        User user = new User();
        user.setAge(20);
        user.show();
        MementoIF memento = user.createMemento();
        user.setAge(25);
        user.show();
        user.loadMemento(memento);
        user.show();
    }
}
