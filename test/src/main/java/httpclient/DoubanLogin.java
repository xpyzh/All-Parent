package httpclient;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by youzhihao on 2017/3/29.
 */
public class DoubanLogin {
    public static void main(String[] args) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        login(httpclient);
        addComment(httpclient);
    }



    public static void login(CloseableHttpClient httpclient) {
        String url = "https://accounts.douban.com/login";
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("form_email", "18512547494"));
        formparams.add(new BasicNameValuePair("form_password", "you120147"));
        formparams.add(new BasicNameValuePair("captcha-solution", "narrow"));//验证码
        formparams.add(new BasicNameValuePair("captcha-id", "ho7CsEPvRMDBBhIGiC08HG36:en"));//验证码
        formparams.add(new BasicNameValuePair("source", "group"));//验证码
        formparams.add(new BasicNameValuePair("redir", "https://www.douban.com/group/topic/98514200/?start=0&post=ok"));//验证码
        formparams.add(new BasicNameValuePair("login", "登录"));//验证码
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            httpPost.addHeader("Cache-Control:", "no-cache");
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
            httpPost.addHeader("Host", "accounts.douban.com");
            httpPost.addHeader("Upgrade-Insecure-Requests", "1");
            httpPost.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            httpPost.addHeader("Origin", "https://www.douban.com");
            httpPost.addHeader("Pragma", "no-cache");
            httpPost.addHeader("Referer", "https://www.douban.com/accounts/login?source=group");
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
            Document doc = Jsoup.parse(response.getEntity().getContent(), "utf-8", url);
            System.out.println(doc.getAllElements());
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
    public static void addComment(CloseableHttpClient httpclient) {
        String url = "https://www.douban.com/group/topic/98514200/add_comment";
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("rv_comment", "ding123"));
        formparams.add(new BasicNameValuePair("ck", "whz-"));
        formparams.add(new BasicNameValuePair("start", "0"));
        formparams.add(new BasicNameValuePair("submit_btn", "加上去"));

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            httpPost.addHeader("Cache-Control:", "no-cache");
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
            httpPost.addHeader("Host", "www.douban.com");
            httpPost.addHeader("Upgrade-Insecure-Requests", "1");
            httpPost.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            httpPost.addHeader("Origin", "https://www.douban.com");
            httpPost.addHeader("Referer", "https://www.douban.com/group/topic/98514200/");
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
            Document doc = Jsoup.parse(response.getEntity().getContent(), "utf-8", url);
            System.out.println(doc.getAllElements());
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
