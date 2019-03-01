package alogrithm;

import com.alibaba.fastjson.JSONObject;

import java.util.Random;

/**
 * Created by youzhihao on 2018/12/24.
 * 堆排序
 */
public abstract class AbstractSort {
    private final static int TEST_TIMES = 100;

    //sort by asc
    public abstract void sort(int[] arr);

    public void testManyTimes() {
        for (int i = 0; i < TEST_TIMES; i++) {
            test();
        }
        System.out.println("congratulate your sort is correct");
    }

    private void test() {
        Random random = new Random();
        int length = random.nextInt(1000);
        //init arrIn
        int[] arrIn = new int[length];
        for (int i = 0; i < arrIn.length; i++) {
            arrIn[i] = random.nextInt(length);
        }
        //println
        System.out.println("[IN]:" + JSONObject.toJSONString(arrIn));
        //sort arrIn by asc
        sort(arrIn);
        //check
        for (int i = 0; i < arrIn.length - 1; i++) {
            if (arrIn[i] > arrIn[i + 1]) {
                throw new RuntimeException("sort error,please check!");
            }
        }
        //println
        System.out.println("[OUT]:" + JSONObject.toJSONString(arrIn));
    }
}
