package composite;

/**
 * Created by youzhihao on 2017/11/22.
 */
public class FileComponent extends Component {

    public FileComponent(String name) {
        super(name);
    }

    //不能再增加其他目录和文件
    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException();
    }

    //不能再增加其他目录和文件
    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void create() {
        System.out.println(String.format("创建文件:%s", this.name));
    }
}
