package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by youzhihao on 2019/3/25.
 *
 */
public class ProcessDemo{

    public static void main(String[] args) {
        demo2();
    }

    // 探究一个流的内部处理过程
    public static void demo1() {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Type.MEAT), new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season fruit", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH), new Dish("salmon", false, 450, Type.FISH));

        List<String> names = menu.stream().filter((Dish d) -> {
            System.out.println("filter:" + d.getName());
            return d.getCalories() > 300;
        }).map(d -> {
            System.out.println("map:" + d.getName());
            return d.getName();
        }).limit(3).collect(Collectors.toList());
        System.out.println(names);
    }

    public static void demo2() {
        List<String> words =Arrays.asList("Hello","World");
        List<String> a = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(a);
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
