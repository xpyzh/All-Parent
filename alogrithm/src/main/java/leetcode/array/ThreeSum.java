package leetcode.array;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 15. 3Sum
 *
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 *
 * The solution set must not contain duplicate triplets.
 *
 * Example:
 *
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 *
 * A solution set is:
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 *
 * Created by youzhihao on 4/4/19.
 */
public class ThreeSum {

    public static void main(String[] args) {
        int[] nums = new int[]{1, -1, -1, 0};
        threeSum3(nums);
    }


    //暴力三层枚举，时间复杂度: n的三次方
    public static List<List<Integer>> threeSum1(int[] nums) {
        //排序
        Arrays.sort(nums);
        //Set<int[]> temp =
        Set<List<Integer>> result = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    }
                }
            }
        }
        return new ArrayList<>(result);
    }

    //两层循环+set，时间复杂度:n的二次方*o(1),空间复杂度o(n)
    public static List<List<Integer>> threeSum2(int[] nums) {
        //排序
        Arrays.sort(nums);
        Set<List<Integer>> result = new HashSet<>();
        for (int i = 0; i < nums.length - 1; i++) {
            if (i >= 1 && nums[i] == nums[i - 1])
                continue;
            Set<Integer> elements = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                int need = 0 - nums[i] - nums[j];
                if (elements.contains(need)) {
                    result.add(Arrays.asList(nums[i], need, nums[j]));
                }
                elements.add(nums[j]);
            }
            elements.add(nums[i]);
        }
        return new ArrayList<>(result);
    }

    //两层循环，第二次用两个下标从两头分别查找
    public static List<List<Integer>> threeSum3(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int sum = 0 - nums[i];
            int headIndex = i + 1;
            int tailIndex = nums.length - 1;
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            while (headIndex < tailIndex) {
                if (nums[headIndex] + nums[tailIndex] < sum) {
                    headIndex++;
                } else if (nums[headIndex] + nums[tailIndex] == sum) {
                    result.add(Arrays.asList(nums[i], nums[headIndex], nums[tailIndex]));
                    while (headIndex < tailIndex && nums[headIndex] == nums[headIndex + 1]) {//把头部相同的全部过掉
                        headIndex++;
                    }
                    while (headIndex < tailIndex && nums[tailIndex] == nums[tailIndex - 1]) {//把尾部相同的全部过掉
                        tailIndex--;
                    }
                    //两边同时向内靠近，因为在没有相同数字的情况下，不可能一边靠近，还能得到相同的和
                    headIndex++;
                    tailIndex--;
                } else {
                    tailIndex--;
                }
            }
        }
        return result;
    }
}
