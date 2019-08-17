package datastruct;

import java.text.MessageFormat;

/**
 * Created by youzhihao on 2019/8/11.
 * 物理结构-链表
 */
public class MyLink {

    //头结点
    private Node head;

    //为节点
    private Node last;

    //记录总数
    private int size;

    public MyLink() {
        this.head = null;
        this.last = null;
    }

    /**
     * 获取Node
     * @param index 元素位置
     */
    private Node get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node curNode = head;
        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }
        return curNode;

    }

    /**
     * 增加元素
     * @param  element 元素
     */
    public void add(int element) {
        Node temp = new Node(element);
        if (size == 0) {
            head = last = temp;
        } else {
            last.next = temp;
            last = temp;
        }
        size++;
    }

    /**
     * 在指定位置插入元素
     * 这里分几种情况考虑:
     * 1.头部插入
     * 2.中间插入
     * 3.尾部插入
     * @param element 元素
     * @param index 元素位置
     */
    public void insert(int element, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node temp = new Node(element);
        if (size == 0) {//空链表
            head = last = temp;
        } else if (index == 0) {//头部插入
            temp.next = head;
            head = temp;

        } else if (index == size) {//尾部插入
            last.next = temp;
            last = temp;
        } else {//中间插入
            Node preNode = get(index - 1);
            temp.next = preNode.next;
            preNode.next = temp;
        }
        size++;
    }

    /**
     * 删除指定位置的元素
     * 这里分几种情况考虑:
     * 1.头部移除
     * 2.中间移除
     * 3.尾部移除
     * @param index  元素位置
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {//头部移除
            head = head.next;
        } else if (index == size) {//尾部移除
            Node preNode = get(index - 1);
            preNode.next = null;
            last = preNode;
        } else {//中间移除
            Node preNode = get(index - 1);
            preNode.next = preNode.next.next;
        }
        size--;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node temp = head;
        for (int i = 0; i < size - 1; i++) {
            sb.append(temp.data + "->");
            temp = temp.next;
        }
        sb.append(temp.data);
        sb.append("]");
        return MessageFormat.format("size={0},link={1}", size, sb.toString());
    }

    private class Node {

        private int data;

        private Node next;

        public Node(int data) {
            this.data = data;
        }
    }

    //测试
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }

    //测试add
    public static void test1() {
        MyLink link = new MyLink();
        link.add(1);
        link.add(2);
        link.add(3);
        link.add(4);
        System.out.println(link);
    }

    //测试remove
    public static void test2() {
        MyLink link = new MyLink();
        link.add(1);
        link.add(2);
        link.add(3);
        link.add(4);
        link.remove(2);//移除index=2的元素
        System.out.println(link);
    }

    //测试insert
    public static void test3() {
        MyLink link = new MyLink();
        link.add(1);
        link.add(2);
        link.add(3);
        link.add(4);
        link.insert(10, 0);//在0位置上插入元素10
        System.out.println(link);
    }

    //测试insert尾部插入
    public static void test4() {
        MyLink link = new MyLink();
        link.add(1);
        link.add(2);
        link.add(3);
        link.add(4);
        link.insert(5, 4);//在4位置上插入元素5
        System.out.println(link);
    }
}
