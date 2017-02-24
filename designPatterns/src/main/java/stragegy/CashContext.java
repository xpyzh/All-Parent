package stragegy;

/**
 * Created by youzhihao on 2017/2/24.
 */
public class CashContext {

    private Cash cash;

    //结合简单工厂模式一起使用
    public CashContext(CashType cashType) {
        switch (cashType) {
            case CASH_NORMAL:
                cash = new CashNormal();
                break;
            case CASH_REBATE_8:
                cash = new CashRebate(0.8);
                break;
            case CASH_RETURN_300_50:
                cash = new CashReturn(300, 50);
                break;
            default:
                cash = new CashNormal();
                break;
        }
    }

    public double pay(double totalPrice) {
        return cash.pay(totalPrice);
    }
}
