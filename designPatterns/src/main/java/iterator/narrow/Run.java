package iterator.narrow;

import iterator.wide.Iterator;
import iterator.wide.List;

/**
 * Created by youzhihao on 2017/11/23.
 * 迭代模式-提供窄接口
 * 提供宽接口：聚集（Aggregate）不提供可以外部可以访问内部元素的方法
 * 通过内部迭代器控制聚集类，进行内部元素迭代
 */
public class Run {

    public static void main(String[] args) {
        Integer[] nums = {1, 2, 3, 4, 5, 6};
        iterator.wide.List<Integer> list = new List<>(nums);
        Iterator<Integer> iterator = list.createIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
