import org.apache.commons.lang.math.RandomUtils;

import java.util.Random;

/**
 * 4、红包算法，输入红包金额和领取人数，返回每个人领取的红包金额,金额单位为分
 * Created by youzhihao on 2019/2/19.
 *
 */
public class Test4 {

    public static void main(String[] args) {
        Long[] reuslt = allocate1(1232245L, 100);
        System.out.println(reuslt);
        long total = 0;
        for (Long tmp : reuslt) {
            total += tmp;
        }
        System.out.println(total);
    }

    public static Long[] allocate(Long amount, int number) {
        long total = amount;
        long min = 1;
        Long[] result = new Long[number];
        for (int i = 1; i < number; i++) {
            int max = (int) (total - min * (number - i));//保证最后每个人都有红包
            int red = new RandomUtils().nextInt(max);//随机出红包
            result[i - 1] = Long.valueOf(red);
            total -= red;
        }
        result[number - 1] = total;//最后一个人分剩下所有红包
        return result;
    }

    public static Long[] allocate1(Long amount, int number) {
        long total = amount;
        long min = 1;
        Long[] result = new Long[number];
        for (int i = 1; i < number; i++) {
            long max = (int) (total - min * (number - i));//保证最后每个人都有红包
            long red = min + (((long) (new Random().nextDouble() * (max - min))));//随机出红包
            result[i - 1] = Long.valueOf(red);
            total -= red;
        }
        result[number - 1] = total;//最后一个人分剩下所有红包
        return result;
    }
}
