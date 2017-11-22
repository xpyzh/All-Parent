package composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youzhihao on 2017/11/22.
 */
public class DirComponent extends Component {

    private List<Component> childList = new ArrayList<>();

    public DirComponent(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        childList.add(component);
    }

    @Override
    public void remove(Component component) {
        childList.remove(component);
    }

    @Override
    public void create() {
        System.out.println(String.format("创建目录:%s", name));
        for (Component child : childList) {
            child.create();
        }
    }
}
