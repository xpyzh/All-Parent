package flink.window;

/**
 * Created by youzhihao on 2018/4/10.
 */
public class TimeModel {

    private Integer key;

    private Integer id;

    private Long time;

    public TimeModel() {
    }

    public TimeModel(Integer key, Integer id, Long time) {
        this.key = key;
        this.id = id;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
