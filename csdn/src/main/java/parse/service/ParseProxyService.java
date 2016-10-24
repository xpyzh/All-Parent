package parse.service;

import com.google.common.base.Strings;
import model.FreeProxy;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by youzhihao on 2016/10/17.
 */
public abstract class ParseProxyService {

    private String url;

    private volatile BlockingDeque<FreeProxy> freeProxyQueue;

    //去重使用,这里放的是成功刷新博客的代理信息
    private volatile ConcurrentHashMap<String, String> effectProxy;

    //页面url
    private volatile ConcurrentLinkedDeque<String> urlDeque = new ConcurrentLinkedDeque<>();

    //抓取页面数量
    private int crawlingPageNum;

    public ParseProxyService(BlockingDeque<FreeProxy> freeProxyQueue, ConcurrentHashMap<String, String> effectProxy, String url, int crawlingPageNum) {
        this.freeProxyQueue = freeProxyQueue;
        this.effectProxy = effectProxy;
        this.crawlingPageNum = crawlingPageNum;
        this.url = url;
        initUrl();
    }

    public void initUrl() {
        for (int i = 1; i <= crawlingPageNum; i++) {
            urlDeque.add(url + i);
        }
    }

    //解析单个页面,添加代理信息到freeProxySet
    public abstract void parseEveryPage(Document document, Set<FreeProxy> freeProxySet);

    public void parse() {
        Set<FreeProxy> freeProxySet = new HashSet<>();
        //先不用代理解析一遍
        parse1(null, freeProxySet);
        if (freeProxySet.size()<=0) {
            initUrl();
            for (Iterator<Map.Entry<String, String>> it = effectProxy.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                FreeProxy freeProxy = new FreeProxy(entry.getKey(), Integer.valueOf(entry.getValue()));
                System.out.println(MessageFormat.format("尝试代理:{0}",freeProxy.getIp()));
                boolean flag = parse1(freeProxy, freeProxySet);
                if (flag) {
                    break;
                }
            }
        }
        freeProxyQueue.addAll(freeProxySet);
    }
    //解析数据主入口
    public boolean parse1(FreeProxy freeProxy, Set<FreeProxy> freeProxySet) {
        while (true) {
            String pageUrl = urlDeque.pollFirst();
            CloseableHttpResponse response = null;
            try {
                if (Strings.isNullOrEmpty(pageUrl)) {
                    return true;
                }
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpGet httpGet = createHttpGet(freeProxy);
                httpGet.setURI(new URI(pageUrl));
                response = httpclient.execute(httpGet);
                Document doc = Jsoup.parse(response.getEntity().getContent(), "utf-8", pageUrl);
                parseEveryPage(doc, freeProxySet);
            } catch (Exception e) {
                System.out.println(MessageFormat.format("爬取[{0}]失败,exception={1}", pageUrl, e.toString()));
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //如果是切换代理解析，则解析出错一次就换代理
                if (freeProxy != null) {
                    return false;
                }
            }
        }
    }

    //将freeProxySet中的信息放到freeProxyQueue中
    public void addAllProxyInfo() {


        initUrl();
    }
    private HttpGet createHttpGet(FreeProxy freeProxy) {

        HttpGet httpGet = new HttpGet();
        RequestConfig.Builder build =
                RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(5000);

        //换代理重试
        if (freeProxy != null) {
            build.setProxy(new HttpHost(freeProxy.getIp(), freeProxy.getPort()));
        }
        RequestConfig config = build.build();
        httpGet.setConfig(config);
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        httpGet.addHeader("Cache-Control:", "max-age=0");
        httpGet.addHeader("Connection", "keep-alive");
        httpGet.addHeader("Host", "www.xicidaili.com");
        httpGet.addHeader("If-None-Match", "W/\"60947bf507a59277bc0b31ae33039c65\"");
        httpGet.addHeader("Upgrade-Insecure-Requests", "1");
        httpGet.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        return httpGet;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public BlockingDeque<FreeProxy> getFreeProxyQueue() {
        return freeProxyQueue;
    }

    public void setFreeProxyQueue(BlockingDeque<FreeProxy> freeProxyQueue) {
        this.freeProxyQueue = freeProxyQueue;
    }

    public ConcurrentHashMap<String, String> getEffectProxy() {
        return effectProxy;
    }

    public void setEffectProxy(ConcurrentHashMap<String, String> effectProxy) {
        this.effectProxy = effectProxy;
    }

    public ConcurrentLinkedDeque<String> getUrlDeque() {
        return urlDeque;
    }

    public void setUrlDeque(ConcurrentLinkedDeque<String> urlDeque) {
        this.urlDeque = urlDeque;
    }

    public int getCrawlingPageNum() {
        return crawlingPageNum;
    }

    public void setCrawlingPageNum(int crawlingPageNum) {
        this.crawlingPageNum = crawlingPageNum;
    }

}
