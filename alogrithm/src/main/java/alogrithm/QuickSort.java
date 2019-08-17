package alogrithm;

import com.alibaba.fastjson.JSONObject;

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
 * 排序实现:
 *  1.单边循环排序: 利用一个mark标记位，进行单方向的交换，初始mark=0，从左到右当有值小于基准值，则mark+1，并将mark+1后的位置和
 *  2.双边循环排序: left和right两个指针从两头进行比较，并交换
 * 核心思想: 分治
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
public class QuickSort {

    public static void main(String[] args) {
        new QuickSort1().testManyTimes();
        new QuickSort2().testManyTimes();
    }

    //双边循环排序实现
    static class QuickSort1 extends AbstractSort {

        @Override
        public void sort(int[] arr) {

            doSort(arr, 0, arr.length - 1);
        }


        public void doSort(int arr[], int startIndex, int endIndex) {
            if (startIndex >= endIndex) {
                return;
            }
            int pivotIndex = partition(arr, startIndex, endIndex);
            //左边继续排序
            doSort(arr, startIndex, pivotIndex - 1);
            //右边继续排序
            doSort(arr, pivotIndex + 1, endIndex);
        }

        public int partition(int arr[], int startIndex, int endIndex) {
            //以startIndex的元素为记住元素，基准元素的获取可以是随机的
            int pivot = arr[startIndex];
            int left = startIndex;
            int right = endIndex;
            while (left != right) {
                //控制right指针比较并向左移(先右指针移动很关键，因为这样基准位置交换才正确)
                while (left < right && arr[right] > pivot) {
                    right--;
                }
                //控制left指针比较并向右移
                while (left < right && arr[left] <= pivot) {
                    left++;
                }
                //交换
                if (left < right) {
                    int tmp = arr[left];
                    arr[left] = arr[right];
                    arr[right] = tmp;
                }

            }
            //基准交换到中间
            arr[startIndex] = arr[left];
            arr[left] = pivot;
            //返回基准值位置
            return left;
        }
    }

    //单边循环排序实现
    static class QuickSort2 extends AbstractSort {

        @Override
        public void sort(int[] arr) {

            doSort(arr, 0, arr.length - 1);
        }


        public void doSort(int arr[], int startIndex, int endIndex) {
            if (startIndex >= endIndex) {
                return;
            }
            int pivotIndex = partition(arr, startIndex, endIndex);
            //左边继续排序
            doSort(arr, startIndex, pivotIndex - 1);
            //右边继续排序
            doSort(arr, pivotIndex + 1, endIndex);
        }

        public int partition(int arr[], int startIndex, int endIndex) {
            //以startIndex的元素为记住元素，基准元素的获取可以是随机的
            int pivot = arr[startIndex];
            int mark = startIndex;
            //从左边开始
            for (int i = startIndex + 1; i <= endIndex; i++) {
                //如果存在小于基准的元素
                if (arr[i] <= pivot) {
                    //给其预留一个位置
                    mark++;
                    //交换
                    int tmp = arr[mark];
                    arr[mark] = arr[i];
                    arr[i] = tmp;
                }
            }
            //基准交换到中间
            arr[startIndex] = arr[mark];
            arr[mark] = pivot;
            return mark;
        }
    }

}
