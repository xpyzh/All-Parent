package httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Set;

/**
 * Created by youzhihao on 2017/7/22.
 * 电影港电影索引,存在redis里面
 * redis命令
 * 1.flushall:清空所有数据
 * 2.info:查看总共key的数量
 */
public class dygang {

    public static void main(String[] args) {
        fetch();
        //search();
    }

    public static void search() {
        Jedis jedis = new Jedis("127.0.0.1", 7000);
        Set<String> values = jedis.keys("*攻壳机动队真人版*");
        for (String value : values) {
            String pageIndex = jedis.get(value);
            System.out.println(pageIndex);
        }

    }


    public static void fetch() {
        Jedis jedis = new Jedis("127.0.0.1", 7000);
        String url = "http://www.dygang.net/bd/index_14.htm";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        httpGet.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            for (int page = 1; page < 1000; page++) {
                Thread.currentThread().sleep(1000);
                url = MessageFormat.format("http://www.dygang.net/bd/index_{0}.htm", page);
                httpGet.setURI(new URI(url));
                response = httpclient.execute(httpGet);
                Document doc = Jsoup.parse(response.getEntity().getContent(), "GBK", url);
                Elements elements = doc.select(".classlinkclass");
                for (int i = 0; i < elements.size(); i++) {
                    jedis.set(elements.get(i).text(), page + "");
                }
            }
            jedis.save();//保存
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
