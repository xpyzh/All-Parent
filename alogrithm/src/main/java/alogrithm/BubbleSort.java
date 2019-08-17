package alogrithm;

/**
 * 冒泡排序
 * 特点:
 *  1.稳定: 相同元素，排序后能保持原有相对的位置
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
 *
 * 其他:
 *  1. 鸡尾酒排序: 基于冒泡排序的另一种优化排序，冒泡排序是从左往右交换，而鸡尾酒排序是先从左往右，然后再从右往左，再从左往右...，从而解决
 *  23456781，这种排序场景。如果用冒泡排序，需要遍历8轮，而用鸡尾酒排序，只需要遍历3轮（第二轮虽然已经有序，但是还需要新一轮来判断有没有元素交换
 *  如果没有元素交换则表示数组完全有序）
 */
public class BubbleSort extends AbstractSort {

    public static void main(String[] args) {
        new BubbleSort().testManyTimes();
    }

    @Override
    public void sort(int[] arr) {
        //优化点1: 记录最后一次交换的位置
        int lastExchangeIndex = 0;
        //优化点1: 记录有序边界位置
        int sortStartIndex = arr.length - 1;
        for (int i = 0; i < arr.length - 1; i++) {
            //优化点2: 记录是否进行了交换
            boolean swap = false;
            for (int j = 0; j < sortStartIndex; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swap = true;
                    lastExchangeIndex = j;
                }
            }
            sortStartIndex = lastExchangeIndex;
            //如果没有交换，则表示数组已经完全有序
            if (!swap) {
                return;
            }

        }
    }
}
