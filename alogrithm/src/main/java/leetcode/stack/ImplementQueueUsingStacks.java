package leetcode.stack;

import java.util.Stack;

/**
 * 232. Implement Queue using Stacks
 *
 * Implement the following operations of a queue using stacks.
 *
 * push(x) -- Push element x to the back of queue.
 * pop() -- Removes the element from in front of queue.
 * peek() -- Get the front element.
 * empty() -- Return whether the queue is empty.
 * Example:
 *
 * MyQueue queue = new MyQueue();
 *
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // returns 1
 * queue.pop();   // returns 1
 * queue.empty(); // returns false
 * Notes:
 *
 * You must use only standard operations of a stack -- which means only push to top, peek/pop from top, size, and is empty operations are valid.
 * Depending on your language, stack may not be supported natively. You may simulate a stack by using a list or deque (double-ended queue), as long as you use only standard operations of a stack.
 * You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).
 */
public class ImplementQueueUsingStacks {

    /**
     * 使用两个栈来实现一个先进先出队列的效果
     * push: 压人输入栈,时间复杂度o(1)
     * pop: 摊销来看时间复杂度o(1)
     *  1. 如果输出栈不为空，则pop输出栈第一个元素
     *  2. 如果输出栈为空,则将输入栈元素全部压入输出栈，然后pop输出栈第一个元素
     * peek: 摊销来看时间复杂度o(1)
     *  1. 如果输出栈不为空，则peek输出栈第一个元素
     *  2. 如果输出栈为空,则将输入栈元素全部压入输出栈，然后peek输出栈第一个元素
     * empty: 输入和输出栈都为空，则为空,时间复杂度o(1)
     */
    class MyQueue1 {

        private final Stack<Integer> inputStack = new Stack<>();

        private final Stack<Integer> outputStack = new Stack<>();

        private Integer front;

        /** Initialize your data structure here. */
        public MyQueue1() {

        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            if (inputStack.empty()) {
                front = x;
            }
            inputStack.push(x);
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            if (outputStack.empty()) {
                while (!inputStack.empty()) {
                    outputStack.push(inputStack.pop());
                }
            }
            return outputStack.pop();
        }

        /** Get the front element. */
        public int peek() {
            if (!outputStack.empty()) {
                return outputStack.peek();
            }
            return front;
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return inputStack.empty() && outputStack.empty();
        }
    }

    /**
     * 使用两个栈来实现一个先进先出队列的效果
     * push:
     *  1.把现有所有元素从输入栈pop出来后，按序push到输出栈
     *  2.push当前元素到输出栈pop出来后，按序push到输入栈
     *  3.把所有元素从输出栈pop
     * pop: 输出栈pop
     * peek: 输出栈peek
     * empty: 输出栈和输入栈不为空
     */
    class MyQueue2 {

        private final Stack<Integer> inputStack = new Stack<>();

        private final Stack<Integer> outputStack = new Stack<>();

        /** Initialize your data structure here. */
        public MyQueue2() {

        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            while (!inputStack.empty()) {
                outputStack.push(inputStack.pop());
            }
            inputStack.push(x);
            while (!outputStack.empty()) {
                inputStack.push(outputStack.pop());
            }

        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            return inputStack.pop();
        }

        /** Get the front element. */
        public int peek() {
            return inputStack.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return inputStack.empty() && outputStack.empty();
        }
    }


/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */

}
