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
        ConcurrentLinkedQueue<FreeProxy> freeProxyQueue = initFreeProxyQueue();
        ConcurrentHashMap<String, String> effectProxy = new ConcurrentHashMap<>();
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        //初始化获取一次代理信息
        new CrawlingProxyTask(freeProxyQueue, 1, 500).run();
        //定时获取最新代理信息
        scheduledExecutor.scheduleAtFixedRate(new CrawlingProxyTask(freeProxyQueue, 1, 500), 5, 5, TimeUnit.MINUTES);
        //刷新博客
        scheduledExecutor.scheduleAtFixedRate(new CsdnRequestTask(freeProxyQueue, effectProxy, 20), 0, 10, TimeUnit.MINUTES);
        //有效代理信息落地
        scheduledExecutor.scheduleAtFixedRate(new EffectProxyWirterTask(effectProxy), 5, 5, TimeUnit.MINUTES);

    }

    //读取筛选过的代理
    private static ConcurrentLinkedQueue<FreeProxy> initFreeProxyQueue() {
        ConcurrentLinkedQueue<FreeProxy> freeProxyQueue = new ConcurrentLinkedQueue();
        String path = CsdnRequestTask.class.getClassLoader().getResource("proxy.txt").getPath();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    String[] strs = line.split(":");
                    freeProxyQueue.add(new FreeProxy(strs[0], Integer.valueOf(strs[1])));
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
