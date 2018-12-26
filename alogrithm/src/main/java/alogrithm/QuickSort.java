package alogrithm;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * Created by youzhihao on 2018/12/24.
 * 快速排序(2-way partitioning)
 * 特点:
 * 1.不稳定: 相同元素，选择排序后不能保持原有的相对位置
 * 2.空间复杂度: o(nlog2^n)
 * 3.时间复杂度: 大多数时间是o(nlog2^n),最坏情况o(n^2)
 * 5.非自适应:随着元数组越来越有序，时间复杂度不变
 * 适用场景: 快速排序健壮性很高，如果不要求稳定性的情况下，快速排序是一个杰出的通用排序方案(虽然3-way partitioning的快速排序更加优化)
 * 一些优化事项:二分后，少的子集用迭代代替递归来控制空间复杂度
 *
 *
 * 伪代码:
 * _# choose pivot_
 * swap a[1,rand(1,n)]
 *
 * _# 2-way partition_
 * k = 1
 * for i = 2:n, if a[i] < a[1], swap a[++k,i]
 * swap a[1,k]
 * _→ invariant: a[1..k-1] < a[k] <= a[k+1..n]_
 *
 * _# recursive sorts_
 * sort a[1..k-1]
 * sort a[k+1,n]
 */
public class QuickSort extends AbstractSort {

    public static void main(String[] args) {
        new QuickSort().testManyTimes();
    }

    @Override
    public void sort(int[] arr) {
        doSort(arr, 0, arr.length - 1);
    }

    public void doSort(int[] arr, int firstIndex, int lastIndex) {
        if (firstIndex >= lastIndex) {
            return;
        }
        //选取第一个数为中轴数
        int baseTmp = arr[firstIndex];
        int i = firstIndex, j = lastIndex + 1;
        //二分，中轴数归位
        while (true) {
            //从左扫描，找到大于基准数的数
            while (arr[++i] < baseTmp) {
                if (i == lastIndex) {
                    break;
                }
            }
            //从右扫描，找到小于基准数的数
            while (arr[--j] > baseTmp) {
                if (j == firstIndex) {
                    break;
                }
            }
            if (i >= j) {//如果左右两个指正相遇，则停止循环
                break;
            }
            //交换
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        //经过相遇后，最后一次a[i]和a[j]的交换
        //a[j]比a[lo]小，a[i]比a[lo]大，所以将基准元素与a[j]交换
        int temp = arr[j];
        arr[j] = arr[firstIndex];
        arr[firstIndex] = temp;
        doSort(arr, firstIndex, j - 1);
        doSort(arr, j + 1, lastIndex);
    }
}
