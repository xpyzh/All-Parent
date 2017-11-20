import java.text.MessageFormat;

/**
 * Created by youzhihao on 2017/10/27.
 */
public class stock {
    public static void main(String[] args) {
        caculte("小天鹅", 10.55, 21);
        caculte("青岛海尔", 4.81, 22);
        caculte("晨光文具", 2.92, 24);
        caculte("海天味业", 3.97, 30);
        caculte("贵州茅台", 67.15, 32);



    }

    //计算合理股价
    public static void caculte(String name, double pb, double roe) {
        double n = 8;//回本周期
        double result = pb * Math.pow((1 + roe / 100), n);
        System.out.println(MessageFormat.format("{0}:{1}", name, result));
    }

}
