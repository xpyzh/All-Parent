package alogrithm;

/**
 * Created by youzhihao on 2018/12/25.
 * 归并排序
 * 特点:
 * 1.稳定: 相同元素，插入排序后能保持原有相对的位置
 * 2.空间复杂度: 额外的o(n)的数组空间
 * 3.时间复杂度: o(nlogn)
 * 5.非自适应:随着元数组越来越有序，时间复杂度不变
 * 适用场景: 如果不在乎额外的空间损耗的话，归并排序是一种比较稳定的排序
 * 伪代码:
 * # split in half
 * m = n / 2
 *
 * # recursive sorts
 * sort a[1..m]
 * sort a[m+1..n]
 *
 * # merge sorted sub-arrays using temp array
 * b = copy of a[1..m]
 * i = 1, j = m+1, k = 1
 * while i <= m and j <= n,
 *     a[k++] = (a[j] < b[i]) ? a[j++] : b[i++]
 *     → invariant: a[1..k] in final position
 * while i <= m,
 *     a[k++] = b[i++]
 *     → invariant: a[1..k] in final position
 */
public class MergeSort extends AbstractSort {
    public static void main(String[] args) {
        new MergeSort().testManyTimes();
    }

    @Override
    public void sort(int[] arr) {
        doSort(arr, 0, arr.length - 1);

    }

    private void doSort(int[] arr, int first, int last) {
        //二分
        if (first >= last) {
            return;
        }
        int mid = (first + last) / 2;
        doSort(arr, first, mid);
        doSort(arr, mid + 1, last);
        merge(arr, first, mid, last);
    }

    private void merge(int[] arr, int first, int mid, int last) {
        int[] tempArr = new int[last - first + 1];
        //先复制一份到临时数组
        int k = 0;
        for (int i = first; i <= last; i++) {
            tempArr[k++] = arr[i];
        }
        int tempMid = (mid - first);//记录临时数组的分界线
        int i = 0;
        int j = tempMid+1;
        int m = first;
        while (i <= tempMid && j < tempArr.length) {
            if (tempArr[i] <= tempArr[j]) {
                arr[m++] = tempArr[i++];
            } else {
                arr[m++] = tempArr[j++];
            }
        }
        while (i <= tempMid) {//如果有，将左边剩余的填入原数组
            arr[m++] = tempArr[i++];
        }
        while (j < tempArr.length) {//如果有，将右边剩余的填入原数组
            arr[m++] = tempArr[j++];
        }
    }
}
