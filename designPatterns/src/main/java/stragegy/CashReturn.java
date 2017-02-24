package stragegy;

/**
 * Created by youzhihao on 2017/2/24.
 * 满减的促销策略
 */
public class CashReturn implements Cash {

    //需要达到的消费金额
    private double moneyCondition;

    //返回的金额
    private double moneyReturn;

    public CashReturn(double moneyCondition, double moneyReturn) {
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public double pay(double totalPrice) {
        if (totalPrice >= moneyCondition) {
            return totalPrice - moneyReturn;
        }
        return totalPrice;
    }
}
