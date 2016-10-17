package command;

import model.FreeProxy;
import task.CrawlingProxyTask;
import task.CsdnRequestTask;
import task.EffectProxyWirterTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.*;

/**
 * Created by youzhihao on 2016/10/14.
 * 启动类
 */
public class Run {

    public static void main(String[] args) {
        BlockingDeque<FreeProxy> freeProxyQueue = initFreeProxyQueue();
        ConcurrentHashMap<String, String> effectProxy = new ConcurrentHashMap<>();
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        //定时获取最新代理信息
        scheduledExecutor.scheduleAtFixedRate(new CrawlingProxyTask(freeProxyQueue,effectProxy, 1, 500), 0, 10, TimeUnit.MINUTES);
        //定时落地有效代理信息
        scheduledExecutor.scheduleAtFixedRate(new EffectProxyWirterTask(effectProxy), 5, 1, TimeUnit.MINUTES);
        //开启刷新博客的消费者线程
        ThreadPoolExecutor consumerExecutors = (ThreadPoolExecutor) Executors.newFixedThreadPool(100, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(new CsdnRequestTask(freeProxyQueue, effectProxy));
                return thread;
            }
        });
        consumerExecutors.prestartAllCoreThreads();


    }

    //读取筛选过的代理
    private static BlockingDeque<FreeProxy> initFreeProxyQueue() {
        BlockingDeque<FreeProxy> freeProxyQueue = new LinkedBlockingDeque<>();
        String path = CsdnRequestTask.class.getClassLoader().getResource("proxy.txt").getPath();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    String[] strs = line.split(":");
                    freeProxyQueue.putFirst(new FreeProxy(strs[0], Integer.valueOf(strs[1])));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return freeProxyQueue;
    }



}