package facade;

/**
 * Created by youzhihao on 2017/3/5.
 * 门面模式（外观模式）
 * 定义高层接口，来组装低层的接口
 * web项目controller,service,dao层结构有点类似门面模式，service组装dao层接口
 * demo:
 * 基金公司，各个股票
 */
public class Run {

    public static void main(String[] args) {
        Fund fund=new Fund();
        fund.buyFund();
        fund.sellFund();

    }

}
