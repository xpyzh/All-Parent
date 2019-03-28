package lambda;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by youzhihao on 2019/3/27.
 * 自定义一个collector，模拟Collectors.toList()
 * 找出热量大于500的dish集合
 */
public class CustomCollector {

    public static void main(String[] args) {
        demo1();
        demo2();
        demo3();

    }

    //使用Collectors.toList()
    public static void demo1() {
        List<Dish> list = initDishList();
        List<String> result = list.stream().filter(d -> d.getCalories() > 500).map(Dish::getName).collect(Collectors.toList());
        System.out.println(JSONObject.toJSONString(result));
    }

    //使用collector的另一个重载方法
    public static void demo2() {
        List<Dish> list = initDishList();
        List<String> result = list.stream().filter(d -> d.getCalories() > 500).map(Dish::getName)
                .collect(ArrayList::new, List::add, List::addAll);
        System.out.println(JSONObject.toJSONString(result));
    }
    //使用自定义的collector
    public static void demo3() {
        List<Dish> list = initDishList();
        List<String> result = list.stream().filter(d -> d.getCalories() > 500).map(Dish::getName)
                .collect(new CustomListCollector<>());
        System.out.println(JSONObject.toJSONString(result));
    }


    //初始化集合
    public static List<Dish> initDishList() {
        return Arrays.asList(
                new Dish("pork", false, 800, Type.MEAT), new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season fruit", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH), new Dish("salmon", false, 450, Type.FISH));
    }

    public static class CustomListCollector<T> implements Collector<T, List<T>, List<T>> {

        @Override
        public Supplier<List<T>> supplier() {//获得容器
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {//累加
            return List::add;
        }

        @Override
        public BinaryOperator<List<T>> combiner() {//合并
            return (t1, t2) -> {
                t1.addAll(t2);
                return t2;
            };
        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.CONCURRENT, Characteristics.IDENTITY_FINISH));
        }
    }


    public static class Dish {

        private final String name;

        private final boolean vegetarian;

        private final int calories;

        private final Type type;

        public Dish(String name, boolean vegetarian, int calories, Type type) {
            this.name = name;
            this.vegetarian = vegetarian;
            this.calories = calories;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public boolean isVegetarian() {
            return vegetarian;
        }

        public int getCalories() {
            return calories;
        }

        public Type getType() {
            return type;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static enum Type {MEAT, FISH, OTHER}
}
