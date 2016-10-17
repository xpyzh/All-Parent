package parse;

import model.FreeProxy;
import org.jsoup.nodes.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by youzhihao on 2016/10/17.
 */
public abstract class ParseProxyService {

    public static String url = "http://www.kuaidaili.com/free/inha/";


    public volatile BlockingDeque<FreeProxy> freeProxyQueue;

    //去重使用,这里放的是成功刷新博客的代理信息
    public volatile ConcurrentHashMap<String, String> effectProxy;


    //去重使用,这里放的是新获取的代理信息
    public Set<FreeProxy> freeProxySet = new HashSet<>();

    //页面url
    public volatile ConcurrentLinkedDeque<String> urlDeque = new ConcurrentLinkedDeque<>();

    //抓取页面数量
    public int crawlingPageNum;

    public ParseProxyService(BlockingDeque<FreeProxy> freeProxyQueue, ConcurrentHashMap<String, String> effectProxy, int crawlingPageNum) {
        this.freeProxyQueue = freeProxyQueue;
        this.effectProxy = effectProxy;
        this.crawlingPageNum = crawlingPageNum;
    }

    public void initUrl() {
        for (int i = 1; i <= crawlingPageNum; i++) {
            urlDeque.add(url + i);
        }
    }

    //解析单个页面,添加代理信息到freeProxySet
    public abstract void parseEveryPage(Document document);

    //将freeProxySet中的信息放到freeProxyQueue中
    public void addAllProxyInfo() {
        freeProxyQueue.addAll(freeProxySet);
        freeProxySet.clear();
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        ParseProxyService.url = url;
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

    public Set<FreeProxy> getFreeProxySet() {
        return freeProxySet;
    }

    public void setFreeProxySet(Set<FreeProxy> freeProxySet) {
        this.freeProxySet = freeProxySet;
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
