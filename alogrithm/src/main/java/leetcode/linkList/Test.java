package leetcode.linkList;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by youzhihao on 2019/3/4.
 *  给定一个单链表，如何检测链表中存在环并确定环的长度，程序实现
 *  A->B->C->D->E->C（C->E->D是一个环）
 */
public class Test {

    //单元测试,构造一个环,第2个开始为一个环
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        head.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode2;
        //ListNode cycleBeginNode = detectCycle1(head);
        ListNode cycleBeginNode = detectCycle2(head);
        if (cycleBeginNode == null) {
            System.out.println("不是一个环");
        } else {
            int length = 0;
            ListNode tempNode = cycleBeginNode;
            do {
                length++;
                tempNode = tempNode.next;
            } while (tempNode != cycleBeginNode);
            System.out.println(MessageFormat.format("是一个环，环的长度为{0}", length));
        }


    }

    //set, 空间复杂度o(n),时间复杂度o(n)
    public static ListNode detectCycle1(ListNode head) {
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

    //双指针，空间复杂度o(1)，时间复杂度o(n)
    public static ListNode detectCycle2(ListNode head) {
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

    //构造一个单向链表数据结构
    public static class ListNode {

        int val;

        ListNode next;

        ListNode(int x) {
            val = x;
        }

    }
}
