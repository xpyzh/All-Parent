package leetcode.heap;

import java.util.PriorityQueue;

/**
 * 703. Kth Largest Element in a Stream
 * Design a class to find the kth largest element in a stream. Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * Your KthLargest class will have a constructor which accepts an integer k and an integer array nums, which contains initial elements from the stream. For each call to the method KthLargest.add, return the element representing the kth largest element in the stream.
 *
 * Example:
 *
 * int k = 3;
 * int[] arr = [4,5,8,2];
 * KthLargest kthLargest = new KthLargest(3, arr);
 * kthLargest.add(3);   // returns 4
 * kthLargest.add(5);   // returns 5
 * kthLargest.add(10);  // returns 5
 * kthLargest.add(9);   // returns 8
 * kthLargest.add(4);   // returns 8
 * Note:
 * You may assume that nums' length ≥ k-1 and k ≥ 1.
 */
public class KthLargestElementInAStream {

    public static void main(String[] args) {
        int k = 3;
        int[] arr = new int[]{4, 5, 8, 2};
        KthLargest1 kthLargest = new KthLargest1(k, arr);
        kthLargest.add(3);   // returns 4
        kthLargest.add(5);   // returns 5
        kthLargest.add(10);  // returns 5
        kthLargest.add(9);   // returns 8
        kthLargest.add(4);   // returns 8
    }

    //使用优先队列
    static class KthLargest1 {

        private final int k;

        private final PriorityQueue<Integer> queue;

        public KthLargest1(int k, int[] nums) {
            this.k = k;
            this.queue = new PriorityQueue<>(k);
            if (nums == null || nums.length <= 0) {
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                this.add(nums[i]);
            }
        }

        public int add(int val) {
            if (queue.size() < k) {
                queue.add(val);
            } else if (queue.peek() < val) {
                queue.poll();
                queue.add(val);
            }
            return queue.peek();
        }
    }

    // 使用小顶堆,每个元素都要比较，所以是n，每次比较的复杂度log(k)
    // 时间复杂度: nlog(k)
    static class KthLargest2{


    }
    //定义一个最小堆
    static class MinHeap{

    }

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
}
