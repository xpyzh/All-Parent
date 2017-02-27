package stragegy;

import java.util.Scanner;

/**
 * Created by youzhihao on 2017/2/24.
 */
public class Run {

    /**策略模式的demo：
     * 商场提供三种促销策略：
     * 1.正常收费(CashNormal)
     * 2.普通打折(CashRebate)
     * 3.满减(CashReturn)
     * */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            //采用满300减50的促销策略
            CashContext cashContext = new CashContext(CashType.CASH_RETURN_300_50);
            System.out.print("请输入商品总金额:");
            double totalMoney = sc.nextDouble();
            if (totalMoney == -1) {
                return;
            }
            //计算实际需要付款金额
            double realPayMoney = cashContext.pay(totalMoney);
            System.out.println("实际需要付款的金额为:" + realPayMoney);
        }

    }

}
