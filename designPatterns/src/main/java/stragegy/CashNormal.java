package stragegy;

/**
 * Created by youzhihao on 2017/2/24.
 * 正常的促销策略
 */
public class CashNormal implements Cash {

    @Override
    public double pay(double totalPrice) {
        return totalPrice;
    }
}
