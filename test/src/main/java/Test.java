/**
 * Created by youzhihao on 2016/9/27.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        //demo1();
        //demo2();

    }

    public static void demo1() throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("关闭资源");
            }
        }));
        while (true) {
            Thread.currentThread().sleep(1000);
            System.out.println("程序结束");
        }
    }

    /**演示java基本类型是传值还是传引用,结果：java基本类型是传值*/
    public static void demo2() {
        int num = 2;
        demo2_1(num);
        System.out.println(num);
    }

    public static void demo2_1(int num) {
        num++;
        System.out.println(num);
    }

    public static void demo3() {
        int nums[] = {1, 2, 3, 4, 5};
    }

}
