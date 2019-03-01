package leetcode.array;

import java.util.HashMap;

/**
 * 1. Two Sum
 *
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class TwoSum {

    //硬算
    public int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * 利用hashTable，hashTable在绝大多是情况下查找的时间复杂度为o(1),总体时间复杂度下降到o(n)
     */
    public int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int needNum = target - nums[i];
            if (map.containsKey(needNum)) {
                return new int[]{map.get(needNum), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
