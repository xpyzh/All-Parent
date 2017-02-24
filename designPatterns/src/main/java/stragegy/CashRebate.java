package stragegy;

/**
 * Created by youzhihao on 2017/2/24.
 * 打折的促销策略
 */
public class CashRebate implements Cash {

    //折扣率，小数
    private double rebate;

    public CashRebate(double rebate) {
        this.rebate = rebate;
    }

    @Override
    public double pay(double totalPrice) {
        return totalPrice * rebate;
    }
}
