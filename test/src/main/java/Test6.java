import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by youzhihao on 2019/8/10.
 */
public class Test6 {
    public static void main(String[] args) {
        //test1();

        testSchedule();
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
    public static void testSchedule()
    {
        Executors.newScheduledThreadPool(2).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println(new Date().toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },0,2, TimeUnit.SECONDS);
    }
}
