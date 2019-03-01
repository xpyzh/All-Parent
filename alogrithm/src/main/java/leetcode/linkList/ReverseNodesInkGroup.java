package leetcode.linkList;

/**
 * 25. Reverse Nodes in k-Group
 *
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 *
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
 *
 * Example:
 *
 * Given this linked list: 1->2->3->4->5
 *
 * For k = 2, you should return: 2->1->4->3->5
 *
 * For k = 3, you should return: 3->2->1->4->5
 *
 * Note:
 *
 * Only constant extra memory is allowed.
 * You may not alter the values in the list's nodes, only nodes itself may be changed.
 */
public class ReverseNodesInkGroup {

    //递归
    public ListNode reverseKGroup1(ListNode head, int k) {
        ListNode cur = head;
        //找到下一组要翻转的头结点
        for (int i = 0; i < k; i++) {
            if (cur == null) {
                return head;
            }
            cur = cur.next;
        }
        //翻转头结点,并得到翻转后的头结点
        cur = reverseKGroup1(cur, k);
        for (int i = 0; i < k; i++) {
            ListNode tmp = head.next;
            head.next = cur;
            cur = head;
            head = tmp;

        }
        return cur;
    }

    //
    public ListNode reverseKGroup2(ListNode head, int k) {
        if (head == null || head.next == null || k < 2) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode tail = dummy, prev = dummy, temp;
        int count;
        while (true) {
            count = k;
            while (count > 0 && tail != null) {
                count--;
                tail = tail.next;
            }
            if (tail == null) break;//Has reached the end
            head = prev.next;//for next cycle
            // prev-->temp-->...--->....--->tail-->....
            // Delete @temp and insert to the next position of @tail
            // prev-->...-->...-->tail-->head-->...
            // Assign @temp to the next node of @prev
            // prev-->temp-->...-->tail-->...-->...
            // Keep doing until @tail is the next node of @prev
            while (prev.next != tail) {
                temp = prev.next;//Assign
                prev.next = temp.next;//Delete

                temp.next = tail.next;
                tail.next = temp;//Insert

            }
            tail = head;
            prev = head;

        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;
        new ReverseNodesInkGroup().reverseKGroup1(node1, 2);

    }
}
