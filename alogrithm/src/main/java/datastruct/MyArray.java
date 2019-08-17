package datastruct;

import java.text.MessageFormat;

/**
 * Created by youzhihao on 2019/8/11.
 * 物理结构-数组
 */
public class MyArray {

    private int[] array;

    private int size;

    public MyArray(int capacity) {
        array = new int[capacity];
        size = 0;
    }

    /**
     * 增加元素
     * @param  element 元素
     */
    public void add(int element) {
        if (size >= array.length) {//扩容
            resize();
        }
        array[size++] = element;
    }

    /**
     * 在指定位置插入元素
     * 这里分几种情况考虑:
     * 1.尾部插入: 和中间插入类似
     * 2.中间插入: index当前位置元素以及后续元素都要向后移动
     * 3.超范围插入: 需要进行数组扩容,调用resize()
     * @param element 元素
     * @param index 元素位置
     */
    public void insert(int element, int index) {
        //只能在已有元素位置插入
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        //扩容
        if (size >= array.length) {
            resize();
        }
        //从右往左，依次移动数组元素
        for (int i = size; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = element;
        size++;
    }

    /**
     * 删除指定位置的元素
     * @param index  元素位置
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;

    }

    /**
     * 扩容，新数组为原数组容量的2倍
     */
    private void resize() {
        //初始化新数组
        int[] arrayNew = new int[array.length * 2];
        //拷贝数组元素到新数组中
        System.arraycopy(array, 0, arrayNew, 0, array.length);
        array = arrayNew;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size - 1; i++) {
            sb.append(array[i] + ",");
        }
        sb.append(array[size - 1]);
        sb.append("]");
        return MessageFormat.format("size={0},array={1}", size, sb.toString());
    }

    //测试
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();

    }

    //测试add
    public static void test1() {
        MyArray list = new MyArray(2);
        list.add(1);
        list.add(2);
        System.out.println(list);
    }

    //测试resize()
    public static void test2() {
        MyArray list = new MyArray(2);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println(list);
    }

    //测试remove
    public static void test3() {
        MyArray list = new MyArray(2);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.remove(2);//移除index=2的元素
        System.out.println(list);
    }

    //测试insert
    public static void test4() {
        MyArray list = new MyArray(2);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.insert(10, 0);//在0位置上插入元素10
        System.out.println(list);
    }

    //测试insert尾部插入
    public static void test5() {
        MyArray list = new MyArray(2);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.insert(5, 4);//在4位置上插入元素5
        System.out.println(list);
    }
}
