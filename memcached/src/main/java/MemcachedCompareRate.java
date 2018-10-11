import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by youzhihao on 2018/10/9.
 */
public class MemcachedCompareRate {

    public static long last_read_offset = 0;

    public static int total = 0;

    public static int hit = 0;

    public static int error = 0;

    public static void main(String[] args) {
        String path = args[0];
        int top_rate = Integer.valueOf(args[1]);
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
        try {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            file.seek(last_read_offset);
            String tmp;
            Map<String, Integer> keyMap = new HashMap<>();
            while ((tmp = file.readLine()) != null) {
                String[] data = tmp.split(",");
                String key = data[0];
                if (keyMap.containsKey(key)) {
                    Integer value = keyMap.get(key);
                    keyMap.put(key, value + 1);
//                    System.out.println(key + ":" + value);
                } else {
                    keyMap.put(key, 1);
                }
            }
            List<MemcachedModel> keyList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
                keyList.add(new MemcachedModel(entry.getKey(), entry.getValue()));
            }
            Collections.sort(keyList, new Comparator<MemcachedModel>() {
                @Override
                public int compare(MemcachedModel o1, MemcachedModel o2) {
                    return o2.getNum() - o1.getNum();
                }
            });
            double rate = new BigDecimal((float) top_rate / 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //compare key
            for (MemcachedModel model : keyList.subList(0, (int) (keyList.size() * rate))) {
                try {

                    Object value_old = client_old.get(model.getKey());
                    Object value_new = client_new.get(model.getKey());
                    //exclude not exist key
                    if (value_old == null) {
                        continue;
                    }
                    System.out.println(model.getKey() + ":" + model.getNum());
                    total += 1;
                    //error num exclude null value
                    if (value_new == null) {
                        continue;
                    }
                    String value_str_old = (String) value_old;
                    String value_str_new = (String) value_new;
                    if (value_str_old.equals(value_str_new)) {
                        hit += 1;
                    }
                } catch (Exception e) {
                    //ignore cast type error
                    error += 1;
                }
            }
            double hit_rate = 0;
            double error_rate = 0;
            if (total != 0) {
                hit_rate = new BigDecimal((float) hit / total).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                error_rate = new BigDecimal((float) error / total).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            System.out.println("rate=" + rate);
            System.out.println(MessageFormat.format("total:{0},hit:{1},error={2},hit_rate:{3},error_rate={4}",
                    total, hit, error, hit_rate, error_rate));
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

