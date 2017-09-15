package httpclient;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by youzhihao on 2016/10/14.
 * 基于模拟http请求,刷CSDN访问量的demo
 */
public class CsdnJob {

    public static String[] urls = {
            "http://blog.csdn.net/xpylq/article/details/52780755",
            "http://blog.csdn.net/xpylq/article/details/52370864"};

    public static void main(String[] args) {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for (String url : urls) {
                    CloseableHttpResponse response = null;
                    try {
                        HttpGet httpGet = new HttpGet(url);
                        httpGet.setConfig(RequestConfig.custom().setProxy(new HttpHost("61.139.104.216",80)).build());
                        httpGet.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                        httpGet.addHeader("Accept-Encoding","gzip, deflate, sdch");
                        httpGet.addHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
                        httpGet.addHeader("Cache-Control:","max-age=0");
                        httpGet.addHeader("Connection","keep-alive");
                        httpGet.addHeader("Host","blog.csdn.net");
                        httpGet.addHeader("If-None-Match","W/\"60947bf507a59277bc0b31ae33039c65\"");
                        httpGet.addHeader("Upgrade-Insecure-Requests","1");
                        httpGet.addHeader("User-Agent","User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                        response = httpclient.execute(httpGet);
                        System.out.println(MessageFormat.format("url:{0},code:{1}", url, response.getStatusLine().getStatusCode()));

                    } catch (Exception e) {
                        e.printStackTrace();
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
        }, 0, 10, TimeUnit.MINUTES);
    }

}
