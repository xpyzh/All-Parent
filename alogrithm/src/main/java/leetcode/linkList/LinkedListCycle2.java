package leetcode.linkList;

import java.util.HashSet;
import java.util.Set;

/**
 * Linked List Cycle II
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 *
 * Note: Do not modify the linked list.
 *
 *
 *
 * Example 1:
 *
 * Input: head = [3,2,0,-4], pos = 1
 * Output: tail connects to node index 1
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 *
 * Example 2:
 *
 * Input: head = [1,2], pos = 0
 * Output: tail connects to node index 0
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 *
 * Example 3:
 *
 * Input: head = [1], pos = -1
 * Output: no cycle
 * Explanation: There is no cycle in the linked list.
 *
 *
 *
 *
 * Follow up:
 * Can you solve it without using extra space?
 */
public class LinkedListCycle2 {

    //set
    public ListNode detectCycle1(ListNode head) {
        Set<String> set = new HashSet<>();
        while (head != null) {
            boolean result = set.add(head.toString());
            if (!result) {
                break;
            }
            head = head.next;
        }
        return head;
    }

    /**
     * 快慢指针:
     * 假设环的的开始位置距离链表起点距离为A，慢指针走了A+B距离后和快指针相遇
     * 因为快指针速度是慢指针的2倍，很容易得出，快指针需要走2(A+B)才能和慢指针相遇，也就是超过慢指针一圈
     * 得出如下表达式:
     * A+B+N=2(A+B) ==> N=A+B
     * 当快慢指针相遇的时候，也就是慢指正在环里走了B距离，这时候距离环的开始除距离为N-B=A。因此只要当相遇的时候，在链表开始处重新出发
     * 一个慢指针，两个慢指针相遇的位置，就是环的开始位置
     *
     */
    public ListNode detectCycle2(ListNode head) {
        ListNode slow1 = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow1 = slow1.next;
            fast = fast.next.next;
            if (slow1 == fast) {
                ListNode slow2 = head;
                while (slow1 != slow2) {
                    slow1 = slow1.next;
                    slow2 = slow2.next;
                }
                return slow1;
            }
        }
        return null;
    }
}
