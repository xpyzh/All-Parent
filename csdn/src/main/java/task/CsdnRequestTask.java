package task;

import model.FreeProxy;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by youzhihao on 2016/10/14.
 * 访问csdn博客的任务
 */
public class CsdnRequestTask implements Runnable {

    private volatile ConcurrentLinkedQueue<FreeProxy> freeProxyQueue;

    private int threadNum;

    private List<String> blogUrls = new ArrayList<>();

    public CsdnRequestTask(ConcurrentLinkedQueue<FreeProxy> freeProxyQueue, int threadNum) {
        this.freeProxyQueue = freeProxyQueue;
        this.threadNum = threadNum;
        initBlogUrls();
    }

    private void initBlogUrls() {
        String path = CsdnRequestTask.class.getClassLoader().getResource("blog.txt").getPath();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            for (String line = bufferedReader.readLine(); line != null; ) {
                blogUrls.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    while (true) {
                        FreeProxy freeProxy = freeProxyQueue.poll();
                        if (freeProxy == null) {
                            break;
                        }
                        HttpGet httpGet = createHttpGet(freeProxy);
                        CloseableHttpResponse response = null;
                        for (String blogUrl : blogUrls) {
                            try {
                                httpGet.setURI(new URI(blogUrl));
                                response = httpclient.execute(httpGet);
                                System.out.println(MessageFormat.format("url:{0},code:{1}", blogUrl, response.getStatusLine().getStatusCode()));
                            } catch (Exception e) {
                            }
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
            });
        }
    }

    private HttpGet createHttpGet(FreeProxy freeProxy) {
        HttpGet httpGet = new HttpGet();
        httpGet.setConfig(RequestConfig.custom().setProxy(new HttpHost(freeProxy.getIp(), freeProxy.getPort())).build());
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
