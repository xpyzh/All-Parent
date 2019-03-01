package leetcode.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 20. Valid Parentheses
 *
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 *
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Note that an empty string is also considered valid.
 *
 * Example 1:
 *
 * Input: "()"
 * Output: true
 * Example 2:
 *
 * Input: "()[]{}"
 * Output: true
 * Example 3:
 *
 * Input: "(]"
 * Output: false
 * Example 4:
 *
 * Input: "([)]"
 * Output: false
 * Example 5:
 *
 * Input: "{[]}"
 * Output: true
 */
public class ValidParentheses {

    /**
     * 最优解: 栈
     * 时间复杂度: o(n)
     */
    public boolean isValid1(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        for (char c : s.toCharArray()) {
            if (!map.containsKey(c)) {//如果是左括号，push
                stack.push(c);
            } else if (stack.empty() || stack.pop() != map.get(c)) {
                return false;
            }
        }
        return stack.empty();
    }

    //更优，因为没有用到map结构
    public boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
                continue;
            } else if (c == '{') {
                stack.push('}');
                continue;
            } else if (c == '[') {
                stack.push(']');
                continue;
            } else {
                if (stack.isEmpty() || c != stack.pop()) {
                    return false;
                }
            }

        }
        return stack.isEmpty();
    }
}
