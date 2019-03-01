package leetcode.linkList;

/**
 * 24. Swap Nodes in Pairs
 *
 * Given a linked list, swap every two adjacent nodes and return its head.
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 *
 *
 *
 * Example:
 *
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 */
public class SwapNodesInPairs {


    //遍历
    public ListNode swapPairs1(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        dummy.next = head;
        while (cur.next != null && cur.next.next != null) {
            ListNode first = cur.next;
            ListNode second = cur.next.next;
            first.next = second.next;
            cur.next = second;
            cur.next.next = first;
            cur = cur.next.next;
        }
        return dummy.next;
    }

    //递归，交换两个节点，返回交换后的第一个节点
    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode tmp = head.next;
        head.next = swapPairs2(head.next.next);
        tmp.next = head;
        return tmp;
    }
}
