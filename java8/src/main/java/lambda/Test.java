package lambda;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by youzhihao on 2017/11/30.
 */
public class Test {

    public static void main(String[] args) {
        test1();
        testFilter();
        testOperateEachObject();
        test2();

    }

    //Lambda表达式允许你直接以内联的形式为函数式接口的抽象方法提供实现，并把整个表达式作为函数式接口的实例
    public static void test1() {
        Runnable r = () -> System.out.println("hello lambda");
        Thread t = new Thread(r);
        t.start();
        Predicate<Integer> predicate = (Integer i) -> true;
        System.out.println(predicate.test(1));
    }


    public static void testFilter() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            nums.add(i);
        }
        //找出大于50的所有数字
        System.out.println(JSONObject.toJSON(filter(nums, (Integer i) -> i > 50)));
        //找出偶数
        System.out.println(JSONObject.toJSON(filter(nums, (Integer i) -> i % 2 == 0)));
    }

    public static void testOperateEachObject() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            nums.add(i);
        }
        operateEachObject(nums, (Integer i) -> System.out.println(String.format("数字:%d", i)));
    }

    /**
     * 使用Predicate函数式接口:在你需要表示一个涉及类型T的布尔表达式时，就可以使用这个接口
     * demo:根据指定函数，过滤集合，返回新的集合
     * @author youzhihao
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> resultList = new ArrayList();
        for (T t : list) {
            if (predicate.test(t)) {
                resultList.add(t);
            }
        }
        return resultList;
    }

    /**
     *  使用Consumer函数式接口:你如果需要访问类型T的对象，并对其执行某些操作，就可以使用 这个接口
     *  demo:遍历数组，并对每个元素进行指定操作
     * @author youzhihao
     */
    public static <T> void operateEachObject(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    public static void test2() {
        User user = new User();
        Runnable r = () -> {
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(user.getAge());
        };
        new Thread(r).start();
        user.setAge(5);
        System.out.println("aaaaa");
    }

}
