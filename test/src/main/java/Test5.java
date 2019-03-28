import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by youzhihao on 2019/3/14.
 */
public class Test5 {

    public static void main(String[] args) {
        demo1();
        //demo2();
        //demo3();
    }

    //固定线程，在一开始没任务的的情况下，jvm中的yzh线程数为0
    public static void demo1() {
        CountDownLatch latch = new CountDownLatch(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryImpl("yzh"));
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //固定线程，不饱和的情况下，观察jvm总线程数,结论:随着时间变化，jvm中yzh的线程数不断增加，直到达到ThreadPoolExecutor设定的值
    public static void demo2() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryImpl("yzh"));
        while (true) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("run");
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //固定线程，都饱和运转的情况下，观察jvm总线程数,jvm中yzh的线程数不断增加，直到达到ThreadPoolExecutor设定的值
    public static void demo3() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryImpl("yzh"));
        for (int i = 0; i < 1000; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("run");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static String demo4() throws Exception {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }


}


