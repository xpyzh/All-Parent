package concurrency;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by youzhihao on 2018/10/25.
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始等待");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "被唤醒");
        });
        thread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "唤醒:" + thread.getName());
        LockSupport.unpark(thread);
    }
}
