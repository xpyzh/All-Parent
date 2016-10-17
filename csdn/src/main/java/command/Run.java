package command;

import model.FreeProxy;
import parse.KuaiDaiLiParseImpl;
import parse.XiCiDaiLiParseImpl;
import parse.service.ParseProxyService;
import task.CrawlingProxyTask;
import task.CsdnRequestTask;
import task.EffectProxyWirterTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
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
        //解析不同网站的service
        //www.kuaidaili.com
        //ParseProxyService parseProxyService = new KuaiDaiLiParseImpl(freeProxyQueue, effectProxy, 10);
        //www.xicidaili.com
        ParseProxyService parseProxyService = new XiCiDaiLiParseImpl(freeProxyQueue, effectProxy, 100);
        //先解析一次，ip被禁则使用文件中存储的代理再试
        new CrawlingProxyTask(parseProxyService, 1).run();
        //定时获取最新代理信息
        scheduledExecutor.scheduleAtFixedRate(new CrawlingProxyTask(parseProxyService, 1), 10, 10, TimeUnit.MINUTES);
        //定时落地有效代理信息
        scheduledExecutor.scheduleAtFixedRate(new EffectProxyWirterTask(effectProxy), 5, 1, TimeUnit.MINUTES);
        //检查剩余代理数量
        scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(MessageFormat.format("剩余代理数量:{0}",freeProxyQueue.size()));
            }
        },0,30,TimeUnit.SECONDS);
        //开启刷新博客的消费者线程
        ThreadPoolExecutor consumerExecutors = (ThreadPoolExecutor) Executors.newFixedThreadPool(500, new ThreadFactory() {
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
