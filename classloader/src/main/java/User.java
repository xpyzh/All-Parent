/**
 * Created by youzhihao on 2018/11/12.
 */
public class User {

    private String name="App ClassLoader";

    @Override
    public String toString() {
        ClassLoader loader = User.class.getClassLoader();
        return loader == null ? "" : loader.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
