package alogrithm;

/**
 * 冒泡排序
 * 特点:
 *  1.稳定: 相同元素，插入排序后能保持原有相对的位置
 *  2.空间复杂度: o(1)
 *  3.比较复杂度: o(n^2)
 *  4.交换复杂度: o(n^2)
 *  5.自适应: 随着元数组越来越有序，时间复杂度接近o(n)
 *  适用场景:和插入排序类似的特点，在同样接近有序的数组排序的场景下，冒泡排序交换次数会多于插入排序，因此插入排序更优
 * 伪代码:
 * for i = 1:n,
 *     swapped = false
 *     for j = n:i+1,
 *         if a[j] < a[j-1],
 *             swap a[j,j-1]
 *             swapped = true
 *     → invariant: a[1..i] in final position
 *     break if not swapped
 * end
 * Created by youzhihao on 2018/12/24.
 */
public class BubbleSort extends AbstractSort {
    public static void main(String[] args) {
        new BubbleSort().testManyTimes();
    }

    @Override
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            boolean swap = false;
            for (int j = arr.length - 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    swap = true;
                }
            }
            if (!swap) {//如果没有一个要交换的元素，则表明已经排好序，直接返回
                break;
            }
        }
    }
}
