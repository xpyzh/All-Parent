package task;

import com.google.common.base.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import parse.ParseProxyService;

import java.text.MessageFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by youzhihao on 2016/10/14.
 * 爬取免费代理数据的类,不能够开太多线程,会被禁ip...
 */
public class CrawlingProxyTask implements Runnable {

    private ExecutorService executor;

    private ParseProxyService parseProxyService;

    //启动线程数量
    private int threadNum;

    public CrawlingProxyTask(ParseProxyService parseProxyService, int threadNum) {
        this.executor = Executors.newFixedThreadPool(threadNum);
        this.threadNum = threadNum;
        this.parseProxyService = parseProxyService;
    }

    @Override
    public void run() {
        if (parseProxyService.getFreeProxyQueue().size() > 10000) {
            System.out.println(MessageFormat.format("目前代理数量:{},超过1w,暂停获取", parseProxyService.getFreeProxyQueue().size()));
            return;
        }
        System.out.println(MessageFormat.format("{0},开始爬取数据", new Date()));
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Document doc = null;
                    while (true) {
                        String pageUrl = parseProxyService.getUrlDeque().pollFirst();
                        try {
                            if (Strings.isNullOrEmpty(pageUrl)) {
                                break;
                            }
                            doc = Jsoup.connect(pageUrl).get();
                            parseProxyService.parseEveryPage(doc);
                        } catch (Exception e) {
                            System.out.println(MessageFormat.format("爬取[{0}]失败", pageUrl));
                        }
                    }
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
            parseProxyService.addAllProxyInfo();
            parseProxyService.initUrl();
            System.out.println(MessageFormat.format("{0},目前代理队列长度:{1}", new Date(), parseProxyService.getFreeProxyQueue().size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
