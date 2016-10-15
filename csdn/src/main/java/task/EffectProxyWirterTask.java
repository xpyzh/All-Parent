package task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by youzhihao on 2016/10/14.
 * 落地有效的代理的任务
 */
public class EffectProxyWirterTask implements Runnable {

    private volatile ConcurrentHashMap<String, String> effectProxy;

    public EffectProxyWirterTask(ConcurrentHashMap<String, String> effectProxy) {
        this.effectProxy = effectProxy;
    }

    @Override
    public void run() {
        System.out.println("开始文件写入");
        String path = System.getProperty("user.dir") + "/csdn/src/main/resources/proxy.txt";
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Iterator<Map.Entry<String, String>> it = effectProxy.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                bufferedWriter.write(entry.getKey() + ":" + entry.getValue());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("结束文件写入");
    }
}
