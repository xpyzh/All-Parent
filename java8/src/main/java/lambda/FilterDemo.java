package lambda;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by youzhihao on 2019/3/13.
 * 写一个过滤集合的参数化方法
 */
public class FilterDemo {

    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("red", 100), new Apple("red", 200), new Apple("yellow", 300));
        System.out.println(JSONObject.toJSONString(filter(list, (Apple a) -> "red".equalsIgnoreCase(a.getColor()))));
        System.out.println(JSONObject.toJSONString(filter(list, (Apple a) -> a.getWeight() > 100)));


    }


    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T tmp : list) {
            if (predicate.test(tmp)) {
                result.add(tmp);
            }
        }
        return result;
    }

    static class Apple {

        private String color;

        private int weight;

        public Apple(String color, int weight) {
            this.color = color;
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

}
