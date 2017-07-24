package httpclient;

import org.apache.commons.lang.StringUtils;
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
        search();
        //index();
    }

    //搜索
    public static void search() {
        Jedis jedis = new Jedis("127.0.0.1", 7000);
        Set<String> values = jedis.keys("*权力*");
        for (String value : values) {
            String pageIndex = jedis.get(value);
            System.out.println(value + ":" + pageIndex);
        }
    }

    public static void index() {
        update();
        fetch();
    }


    //更新索引
    private static void update() {
        Jedis jedis = new Jedis("127.0.0.1", 7000);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        httpGet.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        CloseableHttpResponse response = null;
        boolean stop = false;
        int offset = 0;
        try {
            for (int page = 2; page < 1000; page++) {
                if (stop) {
                    break;
                }
                String url = MessageFormat.format("http://www.dygang.net/bd/index_{0}.htm", page);
                httpGet.setURI(new URI(url));
                response = httpclient.execute(httpGet);
                Document doc = Jsoup.parse(response.getEntity().getContent(), "GBK", url);
                Elements elements = doc.select(".classlinkclass");
                for (int i = 0; i < elements.size(); i++) {
                    String title = elements.get(i).text();
                    if ("明天也有好吃的饭".equals(title)) {
                        String value = jedis.get(title);
                        offset = page - Integer.valueOf(value);
                        stop = true;
                        break;
                    }
                }
            }
            //重新更新索引
            if (offset == 0) {
                return;
            }
            Set<String> values = jedis.keys("*");
            for (String value : values) {
                String pageIndex = jedis.get(value);
                jedis.set(value, pageIndex + offset);
            }
            jedis.save();//保存
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //重新刷新所有页面索引
    private static void fetch() {
        Jedis jedis = new Jedis("127.0.0.1", 7000);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        httpGet.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        CloseableHttpResponse response = null;
        boolean stop = false;
        try {
            for (int page = 2; page < 1000; page++) {
                if (stop) {
                    break;
                }
                //美剧
                String url = MessageFormat.format("http://www.dygang.net/yx/index_{0}.htm", page);
                //电影
                //String url = MessageFormat.format("http://www.dygang.net/bd/index_{0}.htm", page);
                httpGet.setURI(new URI(url));
                response = httpclient.execute(httpGet);
                Document doc = Jsoup.parse(response.getEntity().getContent(), "GBK", url);
                Elements elements = doc.select(".classlinkclass");
                for (int i = 0; i < elements.size(); i++) {
                    String value = jedis.get(elements.get(i).text());
                    //只更新没有的
                    if (StringUtils.isBlank(value)) {
                        jedis.set(elements.get(i).text(), page + "");
                    } else {
                        stop = true;
                        break;
                    }
                }
            }
            jedis.save();//保存
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
