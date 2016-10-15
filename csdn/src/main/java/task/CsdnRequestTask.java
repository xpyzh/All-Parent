package task;

import model.FreeProxy;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by youzhihao on 2016/10/14.
 * 访问csdn博客的任务
 */
public class CsdnRequestTask implements Runnable {

    private volatile ConcurrentLinkedQueue<FreeProxy> freeProxyQueue;

    //存储有效的代理,落地用
    private volatile ConcurrentHashMap<String, String> effectProxy;

    private int threadNum;

    private ExecutorService executor;

    private List<String> blogUrls = new ArrayList<>();

    public CsdnRequestTask(ConcurrentLinkedQueue<FreeProxy> freeProxyQueue, ConcurrentHashMap<String, String> effectProxy, int threadNum) {
        this.freeProxyQueue = freeProxyQueue;
        this.effectProxy = effectProxy;
        this.threadNum = threadNum;
        executor = Executors.newFixedThreadPool(threadNum);
        initBlogUrls();
    }


    @Override
    public void run() {
        //两个队列,一出一进
        System.out.println(MessageFormat.format("{0},开始刷新博客", new Date()));
        ConcurrentLinkedQueue<FreeProxy> freeProxyQueueSwap = freeProxyQueue;
        freeProxyQueue = new ConcurrentLinkedQueue<>();
        CountDownLatch latch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    while (true) {
                        FreeProxy freeProxy = freeProxyQueueSwap.poll();
                        if (freeProxy == null) {
                            break;
                        }
                        HttpGet httpGet = createHttpGet(freeProxy);
                        CloseableHttpResponse response = null;
                        for (String blogUrl : blogUrls) {
                            long startTime = new Date().getTime();
                            try {
                                httpGet.setURI(new URI(blogUrl));
                                response = httpclient.execute(httpGet);
                                int statusCode = response.getStatusLine().getStatusCode();
                                if (statusCode == 200) {
                                    freeProxyQueue.offer(freeProxy);
                                    effectProxy.put(freeProxy.getIp(), String.valueOf(freeProxy.getPort()));
                                }
                                long needTime = (new Date().getTime() - startTime) / 1000;
                                System.out.println(MessageFormat.format("proxy:{0},code:{1},耗时{2}秒", freeProxy.getIp(), statusCode, needTime));
                            } catch (Exception e) {
                                break;
                            } finally {
                                try {
                                    if (response != null) {
                                        response.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
            System.out.println(MessageFormat.format("{0},结束刷新博客,有效代理数量{1}", new Date(), effectProxy.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initBlogUrls() {
        String path = CsdnRequestTask.class.getClassLoader().getResource("blog.txt").getPath();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                blogUrls.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpGet createHttpGet(FreeProxy freeProxy) {
        HttpGet httpGet = new HttpGet();

        RequestConfig config = RequestConfig.custom()
                .setProxy(new HttpHost(freeProxy.getIp(), freeProxy.getPort()))
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .build();
        httpGet.setConfig(config);
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        httpGet.addHeader("Cache-Control:", "max-age=0");
        httpGet.addHeader("Connection", "keep-alive");
        httpGet.addHeader("Host", "blog.csdn.net");
        httpGet.addHeader("If-None-Match", "W/\"60947bf507a59277bc0b31ae33039c65\"");
        httpGet.addHeader("Upgrade-Insecure-Requests", "1");
        httpGet.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        return httpGet;
    }
}
