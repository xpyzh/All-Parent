import java.util.concurrent.Executors;

/**
 * Created by youzhihao on 2019/8/10.
 */
public class Test6 {
    public static void main(String[] args) {
        //test1();
        System.out.println(-1 << 29);
        System.out.println(0 << 29);
        System.out.println(0x61c88647);
    }
    //模拟场景：对象无强引用，但是相关的对象中的部分属性还在使用，但是垃圾回收该对象导致异常
    public static void test1(){
        newSingleThreadPool();
    }

    public static void newSingleThreadPool() {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] bytes = new byte[1024 * 1024 * 4];
                    System.out.println(Thread.currentThread().getName());
                }finally {
                }
            }
        });
    }
}
