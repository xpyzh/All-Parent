package composite;

/**
 * Created by youzhihao on 2017/11/22.
 */
public abstract class Component {

    String name;

    public Component(String name) {
        this.name = name;
    }

    public abstract void add(Component component);

    public abstract void remove(Component component);

    //创建目录
    public abstract void create();


}
