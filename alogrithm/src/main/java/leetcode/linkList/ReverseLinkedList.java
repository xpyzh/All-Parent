package leetcode.linkList;

/**
 * 206. Reverse Linked List
 *
 * Reverse a singly linked list.
 * Example:
 * Input: 1->2->3->4->5->NULL
 * Output: 5->4->3->2->1->NULL
 * Follow up:
 * A linked list can be reversed either iteratively or recursively. Could you implement both?
 */
public class ReverseLinkedList {


    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode tmpNext = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmpNext;
        }
        return pre;
    }
}
