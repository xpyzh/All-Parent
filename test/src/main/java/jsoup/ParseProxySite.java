package jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by youzhihao on 2016/10/14.
 * 基于jsoup的解析提供http代理的网站,爬取免费的代理ip和端口号
 */
public class ParseProxySite {
    public static void main(String[] args)
    {
        String url="http://www.kuaidaili.com/proxylist/1/";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements=doc.select("tbody > tr");
        for(Element element:elements)
        {
            String ip=element.select("[data-title=IP]").text();
            String port=element.select("[data-title=PORT]").text();
        }
    }

}
