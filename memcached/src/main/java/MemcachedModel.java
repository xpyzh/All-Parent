/**
 * Created by youzhihao on 2018/10/9.
 */
public class MemcachedModel {

    private String key;

    private int num;

    public MemcachedModel(String key, int num) {
        this.key = key;
        this.num = num;
    }

    public String getKey() {
        return key;
    }

    public int getNum() {
        return num;
    }

}
