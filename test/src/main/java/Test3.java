import java.util.concurrent.locks.ReentrantLock;

/**
 * 死锁
 * Created by youzhihao on 2019/2/19.
 */
public class Test3 {


    private static ReentrantLock lockA = new ReentrantLock();

    private static ReentrantLock lockB = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("执行1");
                    lock(lockA, lockB);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("执行2");
                    lock(lockB, lockA);
                }
            }
        }).start();

    }

    public static void lock(ReentrantLock lockA, ReentrantLock lockB) {
        try {
            lockA.lock();
            lockB.lock();
        } finally {
            lockA.unlock();
            lockB.unlock();
        }
    }
}
