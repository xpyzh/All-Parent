package facade;

/**
 * Created by youzhihao on 2017/3/5.
 * 投资者只需要买卖基金即可，不需要知道任何股票的细节
 * 基金经理去关心股票的投资
 */
public class Fund {

    private StockA stockA;

    private StockB stockB;

    public Fund() {
        this.stockA = new StockA();
        this.stockB = new StockB();
    }

    //买基金
    public void buyFund() {
        System.out.println("买基金");
        stockA.speculate();
        stockB.speculate();
    }

    //卖基金
    public void sellFund() {
        System.out.println("卖基金");
        stockA.speculate();
        stockB.speculate();
    }
}
