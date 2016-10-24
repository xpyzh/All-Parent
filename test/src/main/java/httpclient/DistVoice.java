package httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * Created by youzhihao on 2016/10/24.
 */
public class DistVoice {

    public static void main(String[] args) {
        String url = "http://dict.youdao.com/dictvoice?audio=hello&type=1";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Accept", "*/*");
            httpGet.addHeader("Accept-Encoding", "identity;q=1, *;q=0");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            httpGet.addHeader("Cache-Control:", "no-cache");
            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("Host", "dict.youdao.com");
            httpGet.addHeader("Pragma", "no-cache");
            httpGet.addHeader("Referer", "http://dict.youdao.com/dictvoice?audio=hello&type=1");
            httpGet.addHeader("User-Agent", "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            response = httpclient.execute(httpGet);
            InputStream in = response.getEntity().getContent();
            FileOutputStream out = new FileOutputStream("");
            byte[] bytes = new byte[1024];
            for (int size = in.read(bytes); size > 0; size = in.read(bytes)) {
                out.write(bytes);
            }
            out.flush();
            out.close();
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
