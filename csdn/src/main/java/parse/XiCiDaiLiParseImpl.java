package parse;

import model.FreeProxy;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parse.service.ParseProxyService;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by youzhihao on 2016/10/17.
 * http://www.xicidaili.com/nn/1
 */
public class XiCiDaiLiParseImpl extends ParseProxyService {

    public static String url = "http://www.xicidaili.com/nn/";
    public XiCiDaiLiParseImpl(BlockingDeque<FreeProxy> freeProxyQueue, ConcurrentHashMap<String, String> effectProxy, int crawlingPageNum) {
        super(freeProxyQueue, effectProxy, url, crawlingPageNum);
    }
    @Override
    public void parseEveryPage(Document document) {
        Elements elements = document.select("tr:gt(1)");
        for (Element element : elements) {
            String ip = element.select("td:eq(1)").text();
            String port = element.select("td:eq(2)").text();
            FreeProxy freeProxy = new FreeProxy(ip, Integer.valueOf(port));
            if (!getEffectProxy().contains(ip)) {
                getFreeProxySet().add(freeProxy);
            }
        }
    }
}
