package prototype;

/**
 * Created by youzhihao on 2017/3/4.
 * 原型模式的demo
 * 原型模式在java中的实现，其实就是实现Cloneable接口
 * 这里实现一个深层复制，另外是有序列化反序列化是一个深层复制的可选方案，操作简便，但是不知道性能怎么样
 * 注意点：
 * 1.数组和集合的clone也是浅层复制，只复制引用
 * 2.包装类星的clone是深层复制
 */
public class Run {

    public static void main(String[] args) {
        Class aClass1 = new Class();
        aClass1.setClassName("春天花花幼儿园1");
        aClass1.setClassGrade(1);
        Student[] students = new Student[2];
        students[0] = new Student("小夫", 3);
        students[1] = new Student("胖虎", 5);
        aClass1.setStudents(students);
        Class aClass2 = (Class) aClass1.clone();
        aClass2.setClassName("春天花花幼儿园2");
        aClass2.setClassGrade(2);
        aClass2.getStudents()[0].setName("小夫爸爸");
        aClass2.getStudents()[0].setAge(30);
        System.out.println("debug对比两个对象！！！");


    }

}
