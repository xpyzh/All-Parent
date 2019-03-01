package leetcode.hashtable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 242. Valid Anagram
 * Given two strings s and t , write a function to determine if t is an anagram of s.
 *
 * Example 1:
 *
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 * Example 2:
 *
 * Input: s = "rat", t = "car"
 * Output: false
 * Note:
 * You may assume the string contains only lowercase alphabets.
 *
 * Follow up:
 * What if the inputs contain unicode characters? How would you adapt your solution to such case?
 */
public class ValidAnagram {


    /**
     * 解法1: s,t两个字符串都是用快排，然后比较。
     * 时间复杂度: 排序的复杂度大多是在o(nlogn),比如常用的的快速排序，比较两个排序好的字符串也是o(n),综合考虑为o(nlogn)
     */
    public boolean isAnagram1(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        if (s.length() != t.length()) {//如果两个字符串长度都不一致，肯定不是字母异位词
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1, str2);
    }

    /**
     * 解法2: 使用hashtable，来存储字符串每一个字母的数量，然后比较。
     * 时间复杂度: 计算每一个字符串每个字母出现次数,为n*o(1)=o(n),比较hashtable，为n*o(1),综合考虑为o(nlogn)
     * 因此使用hashtable的解法在时间复杂度上更优
     */
    public boolean isAnagram2(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        if (s.length() != t.length()) {//如果两个字符串长度都不一致，肯定不是字母异位词
            return false;
        }
        Map<Character, Integer> sMap = caculate(s);
        Map<Character, Integer> tMap = caculate(t);
        for (Map.Entry<Character, Integer> entry : sMap.entrySet()) {
            Character sKey = entry.getKey();
            int value = entry.getValue();
            if (!tMap.containsKey(sKey) || tMap.get(entry.getKey()) != value) {
                return false;
            }
        }
        return true;
    }

    public Map<Character, Integer> caculate(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            if (!map.containsKey(c)) {
                map.put(c, 0);
            }
            map.put(c, map.get(c) + 1);
        }
        return map;
    }

    //解法3: 利用数组和ascii码来解,理论上复杂度也是o(n)
    public static boolean isAnagram3(String s, String t) {
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;

        int[] map = new int[128];
        for (char c : s.toCharArray()) {
            map[c]++;
        }

        for (char c : t.toCharArray()) {
            //这里要注意,因为当减到0的时候，还存在c的话，就会触发这个，说明相同字母c，两个字符串数量不相等，可以直接返回false了
            if (map[c] == 0) return false;
            else map[c]--;
        }

        for (int i = 0; i < 128; i++) {
            if (map[i] != 0) return false;
        }

        return true;

    }
}
