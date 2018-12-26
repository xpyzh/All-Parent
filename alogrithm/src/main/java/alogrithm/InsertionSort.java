package alogrithm;

/**
 * Created by youzhihao on 2018/12/24.
 * 插入排序
 * 特点:
 *  1.稳定: 相同元素，插入排序后能保持原有相对的位置
 *  2.空间复杂度: o(1)
 *  3.比较复杂度: o(n^2)
 *  4.交换复杂度: o(n^2)
 *  5.自适应: 随着元数组越来越有序，时间复杂度接近o(n)
 *  6.开销较小: 非递归调用，因此开销小
 * 适用场景: 数据量较小的场景，已经接近排好序的场景
 * 伪代码:
 * for i = 2:n,
 *     for (k = i; k > 1 and a[k] < a[k-1]; k--)
 *         swap a[k,k-1]
 *     → invariant: a[1..i] is sorted
 * end
 *
 */
public class InsertionSort extends AbstractSort {

    public static void main(String[] args) {
        new InsertionSort().testManyTimes();
    }

    @Override
    public void sort(int[] arr) {
        //假定第一个元素是有序的，从第二个元素开始比较
        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j;
            //每有一个新元素，和前面有序数组的元素挨个比较
            for (j = i; j > 0 && tmp < arr[j - 1]; j--) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
        }
    }
}
