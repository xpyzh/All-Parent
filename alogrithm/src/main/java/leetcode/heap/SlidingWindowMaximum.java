package leetcode.heap;

import java.util.ArrayDeque;

/**
 * 239. Sliding Window Maximum
 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.
 *
 * Example:
 *
 * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 *
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ input array's size for non-empty array.
 *
 * Follow up:
 * Could you solve it in linear time?
 */
public class SlidingWindowMaximum {


    /**
     * 最优解，使用双向队列,保持队列首为最大值
     * 窗口的范围,[i-k+1,i]
     * We scan the array from 0 to n-1, keep "promising" elements in the deque. The algorithm is amortized O(n) as each element is put and polled once.
     *
     * At each i, we keep "promising" elements, which are potentially max number in window [i-(k-1),i] or any subsequent window. This means
     *
     * If an element in the deque and it is out of i-(k-1), we discard them. We just need to poll from the head, as we are using a deque and elements are ordered as the sequence in the array
     *
     * Now only those elements within [i-(k-1),i] are in the deque. We then discard elements smaller than a[i] from the tail. This is because if a[x] <a[i] and x<i, then a[x] has no chance to be the "max" in [i-(k-1),i], or any other subsequent window: a[i] would always be a better candidate.
     *
     * As a result elements in the deque are ordered in both sequence in array and their value. At each step the head of the deque is the max element in [i-(k-1),i]
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }
        int[] result = new int[nums.length - k + 1];//因为初始就是k个元素的窗口，所以从k个元素开始
        ArrayDeque<Integer> deque = new ArrayDeque<>(k);//deque存放数组下标
        int resultIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!deque.isEmpty() && deque.peekFirst() < (i - k + 1)) {//队列最左边的元素是否大于窗口范围
                deque.pollFirst();
            }
            //如果最右边的元素小于num，就移除，没必要存在了，因为不可能是窗口中的最大元素，这一步保证最左边一定是最大的
            while (!deque.isEmpty() && nums[deque.peekLast()] < num) {
                deque.pollLast();
            }
            deque.addLast(i);
            if (i >= k - 1) {
                result[resultIndex++] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}
