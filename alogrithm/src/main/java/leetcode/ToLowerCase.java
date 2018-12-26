package leetcode;

/**
 * Created by youzhihao on 2018/12/23.
 * Implement function ToLowerCase() that has a string parameter str, and returns the same string in lowercase.
 *
 *
 *
 * Example 1:
 *
 * Input: "Hello"
 * Output: "hello"
 * Example 2:
 *
 * Input: "here"
 * Output: "here"
 * Example 3:
 *
 * Input: "LOVELY"
 * Output: "lovely"
 */
public class ToLowerCase {
    public static void main(String[] args) {
    }

    public static String toLowerCase1(String str) {
        return str.toLowerCase();
    }

    public static String toLowerCase2(String str) {
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= 'A' && array[i] <= 'Z') {
                array[i] = (char) (array[i] - 'A' + 'a');
            }
        }
        return new String(array);
    }
}
