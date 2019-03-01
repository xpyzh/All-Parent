package leetcode.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 225. Implement Stack using Queues
 *
 * Implement the following operations of a stack using queues.
 *
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * empty() -- Return whether the stack is empty.
 * Example:
 *
 * MyStack stack = new MyStack();
 *
 * stack.push(1);
 * stack.push(2);
 * stack.top();   // returns 2
 * stack.pop();   // returns 2
 * stack.empty(); // returns false
 * Notes:
 *
 * You must use only standard operations of a queue -- which means only push to back, peek/pop from front, size, and is empty operations are valid.
 * Depending on your language, queue may not be supported natively. You may simulate a queue by using a list or deque (double-ended queue), as long as you use only standard operations of a queue.
 * You may assume that all operations are valid (for example, no pop or top operations will be called on an empty stack).
 */
public class ImplementStackUsingQueues {


    /**
     * 两个队列实现,分别为q1和q2
     * push: push到q1,时间复杂度o(1)
     * pop:  将q1的元素，除了队尾元素，全部push到q2,然后取出q1的元素,把q1和q2交换,时间复杂度o(n)
     * top:  front标记队尾元素,时间复杂度o(1)
     * empty: q1和q2是否都为空,时间复杂度o(1)
     */
    class MyStack1 {

        private Queue<Integer> q1 = new LinkedList<>();

        private Queue<Integer> q2 = new LinkedList<>();

        private int topElement;

        /** Initialize your data structure here. */
        public MyStack1() {

        }

        /** Push element x onto stack. */
        public void push(int x) {
            q1.add(x);
            topElement = x;
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            while (q1.size() > 1) {
                topElement = q1.remove();
                q2.add(topElement);
            }
            Queue<Integer> temp = q1;
            q1 = q2;
            q2 = temp;
            return q2.remove();
        }

        /** Get the top element. */
        public int top() {
            return topElement;
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return q1.isEmpty() && q2.isEmpty();
        }
    }

    /**
     * 两个队列实现,分别为q1和q2
     * push: 先push到q2,将q1元素push到q2,时间复杂度o(n)
     * pop:  q2正常pop
     * top:  front标记队尾元素,时间复杂度o(1)
     * empty: q1和q2是否都为空,时间复杂度o(1)
     */
    class MyStack2 {

        private Queue<Integer> q1 = new LinkedList<>();

        private Queue<Integer> q2 = new LinkedList<>();

        private int topElement;

        /** Initialize your data structure here. */
        public MyStack2() {

        }

        /** Push element x onto stack. */
        public void push(int x) {
            topElement = x;
            q2.add(x);
            while (!q1.isEmpty()) {
                q2.add(q1.remove());
            }
            Queue<Integer> tmp = q1;
            q1 = q2;
            q2 = tmp;
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            int result = q1.remove();
            if (!q1.isEmpty()) {
                topElement = q1.peek();
            }
            return result;
        }

        /** Get the top element. */
        public int top() {
            return topElement;
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return q1.isEmpty() && q2.isEmpty();
        }
    }
}
