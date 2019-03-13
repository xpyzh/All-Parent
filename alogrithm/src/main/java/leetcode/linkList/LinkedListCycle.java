package leetcode.linkList;

import java.util.HashSet;
import java.util.Set;

/**
 * 141. Linked List Cycle
 *
 * Given a linked list, determine if it has a cycle in it.
 *
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 *
 *
 *
 * Example 1:
 *
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 *
 * Example 2:
 *
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 *
 * Example 3:
 *
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 *
 *
 *
 *
 * Follow up:
 *
 * Can you solve it using O(1) (i.e. constant) memory?
 */
public class LinkedListCycle {


    /**
     * 使用HashSet
     * 时间复杂度: o(n),因为要遍历所有节点一次
     * 空间复杂度: o(n),因为要存储所有节点一次
     */
    public boolean hasCycle1(ListNode head) {
        Set<String> address = new HashSet<>();
        while (head != null) {
            Boolean result = address.add(head.toString());
            if (!result) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 快慢双指针
     *
     */
    public boolean hasCycle2(ListNode head) {
        if (head == null) return false;
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }



}
