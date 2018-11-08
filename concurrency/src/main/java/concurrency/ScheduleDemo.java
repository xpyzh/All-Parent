package concurrency;

import java.text.MessageFormat;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by youzhihao on 2018/10/27.
 */
public class ScheduleDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(2);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(MessageFormat.format("start:{0}", System.currentTimeMillis()));
                    Thread.sleep(5000);
                    System.out.println(MessageFormat.format("end:{0}", System.currentTimeMillis()));
//                    throw new RuntimeException("error");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
