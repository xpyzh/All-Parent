package command;

import model.FreeProxy;
import task.CrawlingProxyTask;

import java.util.concurrent.*;

/**
 * Created by youzhihao on 2016/10/14.
 * 启动类
 */
public class Run {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<FreeProxy> freeProxyQueue = new ConcurrentLinkedQueue<>();
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        //初始化获取一次代理信息
        new CrawlingProxyTask(freeProxyQueue, 1, 100).run();
        //定时获取最新代理信息
        scheduledExecutor.scheduleAtFixedRate(new CrawlingProxyTask(freeProxyQueue, 1, 100), 10, 10, TimeUnit.MINUTES);
    }

}
