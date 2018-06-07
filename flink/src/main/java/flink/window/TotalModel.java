package flink.window;

/**
 * Created by youzhihao on 2018/4/10.
 */
public class TotalModel {

    private int Total;

    public TotalModel() {
    }

    public TotalModel(int total) {
        Total = total;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
