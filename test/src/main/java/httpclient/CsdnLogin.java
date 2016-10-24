package httpclient;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
 * Created by youzhihao on 2016/10/20.
 */
public class CsdnLogin {

    public static void main(String[] args) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        List<NameValuePair> pairs = loginIndex(httpclient);
        login(httpclient, pairs);
        agree(httpclient);

    }

    public static List<NameValuePair> loginIndex(CloseableHttpClient httpclient) {

        String url = "http://passport.csdn.net/account/login";
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            //httpGet.setConfig(RequestConfig.custom().setProxy(new HttpHost("61.139.104.216", 80)).build());
            httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            httpGet.addHeader("Cache-Control:", "max-age=0");
            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("Host", "passport.csdn.net");
            httpGet.addHeader("If-None-Match", "W/\"60947bf507a59277bc0b31ae33039c65\"");
            httpGet.addHeader("Upgrade-Insecure-Requests", "1");
            httpGet.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            response = httpclient.execute(httpGet);
            Document doc = Jsoup.parse(response.getEntity().getContent(), "utf-8", url);
            String lt = doc.select("input[name=lt]").val();
            String execution = doc.select("input[name=execution]").val();
            formparams.add(new BasicNameValuePair("username", "1121544708@qq.com"));
            formparams.add(new BasicNameValuePair("password", "you3265012"));
            formparams.add(new BasicNameValuePair("lt", lt));
            formparams.add(new BasicNameValuePair("execution", execution));
            formparams.add(new BasicNameValuePair("_eventId", "submit"));
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
        return formparams;

    }


    public static void login(CloseableHttpClient httpclient, List<NameValuePair> formparams) {
        String url = "https://passport.csdn.net/account/login?ref=toolbar";
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            httpPost.addHeader("Cache-Control:", "no-cache");
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
            httpPost.addHeader("Host", "passport.csdn.net");
            httpPost.addHeader("Upgrade-Insecure-Requests", "1");
            httpPost.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            httpPost.addHeader("Origin", "https://passport.csdn.net");
            httpPost.addHeader("Pragma", "no-cache");
            httpPost.addHeader("Referer", "https://passport.csdn.net/account/login?ref=toolbar");
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
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
    public static void agree(CloseableHttpClient httpclient)
    {
        String url = "http://blog.csdn.net/xpylq/article/digg?ArticleId=52370864";
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Accept", "*/*");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            httpGet.addHeader("Cache-Control:", "max-age=0");
            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("Host", "blog.csdn.net");
            httpGet.addHeader("Referer","http://blog.csdn.net/xpylq/article/details/52370864");
            httpGet.addHeader("Upgrade-Insecure-Requests", "1");
            httpGet.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            httpGet.addHeader("X-Requested-With","XMLHttpRequest");
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
