package stragegy;

/**
 * Created by youzhihao on 2017/2/24.
 * 定义一个支付接口，实现一个支付方法
 */
public interface Cash {

    /**
     * 付款方法
     * @param  totalPrice 商品总价值
     * @return 实际需要支付的金额
     * @author youzhihao
     */
    double pay(double totalPrice);

}
