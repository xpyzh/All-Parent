package datastruct;

/**
 * Created by youzhihao on 2019/8/17.
 * 逻辑结构-优先级队列
 * 1.基于二叉堆实现,这里构建一个最大二叉堆
 */
public class MyPriorityQueue {

    private int[] arr;

    private int size;

    public MyPriorityQueue(int capacity) {
        this.arr = new int[capacity];
    }

    /**
     * 入队操作，实质就是将元素放在二叉堆最末尾，然后进行二叉堆的上浮(upAdjust)操作
     */
    public void enQueue(int element) {
        arr[size++] = element;
        upAdjust();
        //判断是否需要扩容
        if (size >= arr.length) {
            resize();
        }
    }

    /**
     * 出队操作,实质是将二叉堆顶部元素移出，并将二叉堆末尾元素移动到顶部，然后进行二叉堆的下沉操作(downAdjust)操作
     */
    public int deQueue() {
        int element = arr[0];
        arr[0] = arr[size - 1];
        downAdjust();
        size--;
        return element;
    }

    /**
     * 扩容操作,进行两倍扩容
     */
    public void resize() {
        int[] arrNew = new int[arr.length * 2];
        System.arraycopy(arr, 0, arrNew, 0, arr.length);
        arr = arrNew;
    }

    public int getSize() {
        return size;
    }

    /**
     * 最大二叉堆，进行上浮操作
     */
    private void upAdjust() {
        int childIndex = size - 1;
        //这里注意，计算parentIndex可以不用考虑是左还在还是右孩子，因为:
        //leftChild=2parent+1 -> parent=(leftChild-1)/2
        //rightChild=2parent+2 -> parent=(rightChild-2)/2 -> parent=(rightChild-1)/2
        //反推的时候，(rightChild-1)/2，只去整除部分，一样得到的是父节点位置
        int parentIndex = (childIndex - 1) / 2;
        int temp = arr[childIndex];
        //上浮判断条件
        while (childIndex > 0 && temp > arr[parentIndex]) {
            arr[childIndex] = arr[parentIndex];
            childIndex = parentIndex;
            parentIndex = (childIndex - 1) / 2;
        }
        arr[childIndex] = temp;
    }

    /**
     * 最大二叉堆，进行下沉操作
     */
    private void downAdjust() {
        int parentIndex = 0;
        int childIndex = 1;
        int temp = arr[parentIndex];
        while (childIndex < size) {
            //如果有右孩子，并且右孩子比左孩子大，则取右孩子
            if (childIndex + 1 < size && arr[childIndex] < arr[childIndex + 1]) {
                childIndex = childIndex + 1;
            }
            //如果父元素小于两个孩子中的最小元素，则交换
            if (temp < arr[childIndex]) {
                arr[parentIndex] = arr[childIndex];
                parentIndex = childIndex;
                childIndex = 2 * parentIndex + 1;
            } else {
                break;
            }
        }
        arr[parentIndex] = temp;
    }

    public static void main(String[] args) {
        test1();
        System.out.println("----------");
        test2();
    }

    //测试优先级队列的基本功能
    public static void test1() {
        MyPriorityQueue priorityQueue = new MyPriorityQueue(5);
        priorityQueue.enQueue(5);
        priorityQueue.enQueue(2);
        priorityQueue.enQueue(3);
        priorityQueue.enQueue(6);
        priorityQueue.enQueue(1);
        int size = priorityQueue.getSize();
        for (int i = 0; i < size; i++) {
            System.out.println(priorityQueue.deQueue());
        }
    }

    //测试优先级队列的扩容
    public static void test2() {
        MyPriorityQueue priorityQueue = new MyPriorityQueue(5);
        priorityQueue.enQueue(5);
        priorityQueue.enQueue(2);
        priorityQueue.enQueue(3);
        priorityQueue.enQueue(6);
        priorityQueue.enQueue(1);
        priorityQueue.enQueue(10);
        int size = priorityQueue.getSize();
        for (int i = 0; i < size; i++) {
            System.out.println(priorityQueue.deQueue());
        }
    }
}

