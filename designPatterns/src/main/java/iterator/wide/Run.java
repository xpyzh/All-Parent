package iterator.wide;

/**
 * Created by youzhihao on 2017/11/23.
 * 迭代模式-提供宽接口
 * 提供宽接口：聚集（Aggregate）提供可以访问内部元素的方法
 * 外部迭代器直接直接使用这些方法，实现迭代
 */
public class Run {

    public static void main(String[] args) {
        Integer[] nums = {1, 2, 3, 4, 5, 6};
        List<Integer> list = new List<>(nums);
        Iterator<Integer> iterator = list.createIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
