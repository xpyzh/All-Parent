package alogrithm;

/**
 * Created by youzhihao on 2018/12/24.
 * 选择排序
 * 特点:
 *  1.不稳定: 相同元素，选择排序后不能保持原有的相对位置
 *  2.空间复杂度:o(1)
 *  3.比较复杂度:o(n^2)
 *  4.交换复杂度:o(n^2)
 *  5.非自适应:随着元数组越来越有序，时间复杂度不变
 *  适用场景:因为不能自适应，一般场景不适用，但是如果交换成本很大的时候，比较适用，因为选择排序的交换复杂度始终为o(n)
 * 伪代码:
 * for i = 1:n,
 *     k = i
 *     for j = i+1:n, if a[j] < a[k], k = j
 *     → invariant: a[k] smallest of a[i..n]
 *     swap a[i,k]
 *     → invariant: a[1..i] in final position
 * end
 */
public class SelectionSort extends AbstractSort {
    public static void main(String[] args) {
        new SelectionSort().testManyTimes();
    }

    @Override
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {//避免一次无用的交换
                int tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }

        }
    }
}
