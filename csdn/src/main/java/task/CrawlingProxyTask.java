package task;

import com.google.common.base.Strings;
import model.FreeProxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by youzhihao on 2016/10/14.
 * 爬取免费代理数据的类,不能够开太多线程,会被禁ip...
 */
public class CrawlingProxyTask implements Runnable {

    private volatile ConcurrentLinkedQueue<FreeProxy> freeProxyQueue;

    private volatile ConcurrentLinkedDeque<String> urlDeque = new ConcurrentLinkedDeque<>();

    private ExecutorService executor;


    //启动线程数量
    private int threadNum;

    //抓取页面数量
    private int crawlingPageNum;

    //免费代理的网址
    public static String url = "http://www.kuaidaili.com/free/inha/";

    public CrawlingProxyTask(ConcurrentLinkedQueue<FreeProxy> freeProxyQueue, int threadNum, int crawlingPageNum) {
        this.freeProxyQueue = freeProxyQueue;
        executor = Executors.newFixedThreadPool(threadNum);
        this.threadNum = threadNum;
        this.crawlingPageNum = crawlingPageNum;
        initUrl();
    }

    //初始化页面地址
    private void initUrl() {
        for (int i = 1; i <= crawlingPageNum; i++) {
            urlDeque.add(url + i);
        }
    }

    @Override
    public void run() {
        System.out.println(MessageFormat.format("{0},开始爬取数据", new Date()));
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Document doc = null;
                    while (true) {
                        String pageUrl = urlDeque.pollFirst();
                        try {
                            if (Strings.isNullOrEmpty(pageUrl)) {
                                break;
                            }
                            doc = Jsoup.connect(pageUrl).get();
                            Elements elements = doc.select("tbody > tr");
                            for (Element element : elements) {
                                String ip = element.select("[data-title=IP]").text();
                                String port = element.select("[data-title=PORT]").text();
                                FreeProxy freeProxy = new FreeProxy(ip, Integer.valueOf(port));
                                freeProxyQueue.offer(freeProxy);
                            }
                        } catch (Exception e) {
                            urlDeque.addLast(pageUrl);
                            System.out.println(MessageFormat.format("爬取[{0}]失败", pageUrl));
                        }
                    }
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
            System.out.println(MessageFormat.format("{0},共爬取代理数量:{1}", new Date(), freeProxyQueue.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
