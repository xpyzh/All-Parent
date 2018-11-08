package concurrency;

import java.text.MessageFormat;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by youzhihao on 2018/10/26.
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        testShutDown();

    }


    /**
     * 目的: 测试shutdown()之后，还能不能execute任务了
     * 结论: 调用shutdown(),新任务的提交会拒绝，正在执行的和队列中的任务会继续执行
     *
     * @author youzhihao
     */
    public static void testShutDown() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        for (int i = 1; i <= 2; i++) {
            executor.execute(new CustomTask(i));
        }
        executor.shutdown();
        executor.execute(new CustomTask(100));

    }

    static class CustomTask implements Runnable {
        private int taskId;

        public CustomTask(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            try {
                System.out.println(MessageFormat.format("taskId={0},start", taskId));
                Thread.sleep(5000);
                System.out.println(MessageFormat.format("taskId={0},end", taskId));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
