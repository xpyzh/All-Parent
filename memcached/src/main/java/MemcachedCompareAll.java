import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by youzhihao on 2018/10/9.
 */
public class MemcachedCompareAll {

    public static long last_read_offset = 0;

    public static int total = 0;

    public static int hit = 0;

    public static int error = 0;

    public static void main(String[] args) {
        String path = args[0];
        List<String> addrs_old = new ArrayList<>();
        addrs_old.add("10.160.246.134:11211");
        addrs_old.add("10.160.246.40:11211");
        addrs_old.add("10.160.246.41:11211");
        List<String> addrs_new = new ArrayList<>();
        addrs_new.add("10.200.177.108:11211");
        addrs_new.add("10.200.177.109:11211");
        addrs_new.add("10.200.177.110:11211");
        MemcachedClient client_old = null;
        MemcachedClient client_new = null;
        try {
            client_old = new MemcachedClient(addrs_old);
            client_new = new MemcachedClient(addrs_new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            int increment = 0;
            int increment_hit = 0;
            try {
                RandomAccessFile file = new RandomAccessFile(path, "r");
                long len = file.length();
                if (len < last_read_offset) {
                    last_read_offset = 0;
                    total = 0;
                    hit = 0;
                    error = 0;
                    System.out.println("restart read file");
                } else {
                    file.seek(last_read_offset);
                    String tmp;
                    List<String> keyList = new ArrayList<>();
                    while ((tmp = file.readLine()) != null) {
                        String[] data = tmp.split(",");
                        String key = data[0];
                        keyList.add(key);

                    }
                    //sleep 1 second ,wait syn data to "mirror cache server"
                    Thread.sleep(1000);
                    //compare key
                    for (String key : keyList) {
                        try {

                            Object value_old = client_old.get(key);
                            Object value_new = client_new.get(key);
                            //exclude not exist key
                            if (value_old == null) {
                                continue;
                            }
                            total += 1;
                            increment += 1;
                            //error num exclude null value
                            if (value_new == null) {
                                continue;
                            }
                            String value_str_old = (String) value_old;
                            String value_str_new = (String) value_new;
                            if (value_str_old.equals(value_str_new)) {
                                hit += 1;
                                increment_hit += 1;
                            }
                        } catch (Exception e) {
                            //ignore cast type error
                            error += 1;
                            continue;
                        }
                    }
                    last_read_offset = len;
                }
                double hit_rate = 0;
                double error_rate = 0;
                if (total != 0) {
                    hit_rate = new BigDecimal((float) hit / total).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                    error_rate = new BigDecimal((float) error / total).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                System.out.println(MessageFormat.format("total:{0},hit:{1},error={2},hit_rate:{3},error_rate={4},increment={5},increment_hit={6}",
                        total, hit, error, hit_rate, error_rate, increment, increment_hit));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
