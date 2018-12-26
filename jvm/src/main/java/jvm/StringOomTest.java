package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youzhihao on 2018/11/29.
 * 模拟string的溢出
 * 目的:比较jdk6，7,8字符串常量在jvm内存中的位置变化
 * jdk6:Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
 * jdk7:Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * jdk8:Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 *
 */
public class StringOomTest {
    static String base = "string";

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String str = base + base;
            base = str;
            list.add(str.intern());
        }
    }
}
