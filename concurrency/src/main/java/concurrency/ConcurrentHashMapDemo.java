package concurrency;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by youzhihao on 2018/10/25.
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(10);
        map.put("name", "youzhihao");
        if (map.containsKey("name")) {
            System.out.print("1");
        } else if (map.containsKey("name")) {
            System.out.print("2");
        }

    }
}
