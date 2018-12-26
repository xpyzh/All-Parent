package redis;

import redis.clients.jedis.Jedis;

/**
 * Created by youzhihao on 2018/12/14.
 */
public class ClusterRouteTest {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("127.0.0.1",7000);
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        System.out.println(jedis.get("name"));
    }
}
