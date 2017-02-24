package stragegy;

/**
 * Created by youzhihao on 2017/2/24.
 */
public enum CashType {

    CASH_NORMAL(0, "普通策略"),
    CASH_REBATE_8(1, "打八折策略"),
    CASH_RETURN_300_50(2, "300反50");

    private int value;

    private String desc;

    CashType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
