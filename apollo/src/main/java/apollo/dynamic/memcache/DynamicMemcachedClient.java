package apollo.dynamic.memcache;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.QueueFullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by youzhihao on 2018/9/26.
 * dynamic change memcached client
 */
@EnableApolloConfig("dynamic-memcached")
public class DynamicMemcachedClient extends MemcachedClient implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger("dynamic-memcached");


    private ConnectionFactory cf;

    private String addrs;

    private int clientInnerCount;

    private MemcachedClient defaultTargetClient;


    public DynamicMemcachedClient(ConnectionFactory cf, String addrs, int clientInnerCount) throws IOException {
        //ignore(extend MemcachedClient because of accommodate stale business code,save reconstruct time）
        super(new ArrayList<>());
        this.cf = cf;
        this.addrs = addrs;
        this.clientInnerCount = clientInnerCount;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.addrs = this.addrs.replace(";", ",");
        this.defaultTargetClient = new MemcachedClient(cf, Arrays.asList(this.addrs.split(",")), clientInnerCount);
    }

    @ApolloConfigChangeListener("dynamic-memcached")
    public void onChange(ConfigChangeEvent changeEvent) {
        if (changeEvent.isChanged("memcached.addrs")) {
            try {
                String oldAddrs = changeEvent.getChange("memcached.addrs").getOldValue();
                String newAddrs = changeEvent.getChange("memcached.addrs").getNewValue();
                setAddrs(newAddrs);
                afterPropertiesSet();
                logger.info("change memcached ip success! oldAddrs={},newAddrs={}", oldAddrs, newAddrs);
            } catch (Throwable e) {
                logger.error("change memcached ip fail!", e);
            }

        }
    }

    public boolean add(String _key, Object _value, Integer _hashCode) {
        return defaultTargetClient.add(_key, _value, _hashCode);
    }


    public boolean add(String _key, Object _value, Long _exp, Integer _hashCode) {
        return defaultTargetClient.add(_key, _value, _exp, _hashCode);
    }


    public boolean add(String _key, Object _value, Long _expiry) {
        return defaultTargetClient.add(_key, _value, _expiry);
    }


    public boolean add(String _key, Object _value) {
        return defaultTargetClient.add(_key, _value);
    }


    public boolean addCounter(String _key, Long _counter, Integer _hashCode) {
        return defaultTargetClient.addCounter(_key, _counter, _hashCode);
    }


    public boolean addCounter(String _key, Long _counter, Long _expiry, Integer _hashCode) {
        return defaultTargetClient.addCounter(_key, _counter, _expiry, _hashCode);
    }


    public boolean addCounter(String _key, Long _counter) {
        return defaultTargetClient.addCounter(_key, _counter);
    }


    public Future<Boolean> asyncAdd(String _key, Object _value, Long _expiry, Integer _hashCode) {
        return defaultTargetClient.asyncAdd(_key, _value, _expiry, _hashCode);
    }


    public Future<Boolean> asyncDelete(String _key, int _when, Integer _hashCode) {
        return defaultTargetClient.asyncDelete(_key, _when, _hashCode);
    }


    public Future<Boolean> asyncFlushAll() {
        return defaultTargetClient.asyncFlushAll();
    }


    public Future<Object> asyncGet(String _key, Integer _hashCode, boolean _asString) throws UnsupportedEncodingException {
        return defaultTargetClient.asyncGet(_key, _hashCode, _asString);
    }


    public Future<Map<String, Object>> asyncGetBulk(String[] _keys, Integer _hashCode) {
        return defaultTargetClient.asyncGetBulk(_keys, _hashCode);
    }


    public Future<Map<String, Object>> asyncGetBulk(String[] _keys, Integer[] _hashCodes) {
        return defaultTargetClient.asyncGetBulk(_keys, _hashCodes);
    }


    public Future<Boolean> asyncReplace(String _key, Object _value, Long _expiry, Integer _hashCode) {
        return defaultTargetClient.asyncReplace(_key, _value, _expiry, _hashCode);
    }


    public Future<Boolean> asyncSet(String _key, Object _value, Long _exp, Integer _hashCode) {
        return defaultTargetClient.asyncSet(_key, _value, _exp, _hashCode);
    }


    public long decr(String _key, long _by, Integer _hashCode) {
        return defaultTargetClient.decr(_key, _by, _hashCode);
    }


    public long decr(String _key, long _by) {
        return defaultTargetClient.decr(_key, _by);
    }


    public long decr(String _key) {
        return defaultTargetClient.decr(_key);
    }


    public boolean delete(String _key, Integer _hashCode, Long _expiry) {
        return defaultTargetClient.delete(_key, _hashCode, _expiry);
    }


    public boolean delete(String _key, Integer _hashCode) {
        return defaultTargetClient.delete(_key, _hashCode);
    }


    public boolean failDelete(String key, Integer hashCode) throws QueueFullException {
        return defaultTargetClient.failDelete(key, hashCode);
    }


    public boolean delete(String _key, Long _expiry) {
        return defaultTargetClient.delete(_key, _expiry);
    }


    public boolean delete(String _key) {
        return defaultTargetClient.delete(_key);
    }


    public boolean flushAll() {
        return defaultTargetClient.flushAll();
    }


    public Object get(String _key, Integer _hashCode, boolean _asString) {
        return defaultTargetClient.get(_key, _hashCode, _asString);
    }


    public Object get(String _key, Integer _hashCode) {
        return defaultTargetClient.get(_key, _hashCode);
    }


    public Object get(String _key) {
        return defaultTargetClient.get(_key);
    }


    public long getCounter(String _key, Integer _hashCode) {
        return defaultTargetClient.getCounter(_key, _hashCode);
    }


    public long getCounter(String _key) {
        return defaultTargetClient.getCounter(_key);
    }


    public Map<String, Object> getMulti(String[] _keys, Integer _hashCode) {
        return defaultTargetClient.getMulti(_keys, _hashCode);
    }


    public Map<String, Object> getMulti(String[] _keys, Integer[] _hashCodes) {
        return defaultTargetClient.getMulti(_keys, _hashCodes);
    }


    public Map<String, Object> getMulti(String[] _keys) {
        return defaultTargetClient.getMulti(_keys);
    }


    public Object[] getMultiArray(String[] _keys, Integer[] _hashCodes) {
        return defaultTargetClient.getMultiArray(_keys, _hashCodes);
    }


    public Object[] getMultiArray(String[] _keys) {
        return defaultTargetClient.getMultiArray(_keys);
    }


    public Map<SocketAddress, Map<String, String>> getStats() {
        return defaultTargetClient.getStats();
    }


    public Map<SocketAddress, String> getVersions() {
        return defaultTargetClient.getVersions();
    }


    public long incr(String _key, long _by, Integer _hashCode) {
        return defaultTargetClient.incr(_key, _by, _hashCode);
    }


    public long incr(String _key, long _by) {
        return defaultTargetClient.incr(_key, _by);
    }


    public long incr(String _key) {
        return defaultTargetClient.incr(_key);
    }


    public boolean keyExists(String _key, Integer _hashCode) {
        return defaultTargetClient.keyExists(_key, _hashCode);
    }


    public boolean keyExists(String _key) {
        return defaultTargetClient.keyExists(_key);
    }


    public boolean replace(String _key, Object _value, Integer _hashCode) {
        return defaultTargetClient.replace(_key, _value, _hashCode);
    }


    public boolean replace(String _key, Object _value, Long _expiry, Integer _hashCode) {
        return defaultTargetClient.replace(_key, _value, _expiry, _hashCode);
    }


    public boolean replace(String _key, Object _value, Long _expiry) {
        return defaultTargetClient.replace(_key, _value, _expiry);
    }


    public boolean replace(String _key, Object _value) {
        return defaultTargetClient.replace(_key, _value);
    }


    public boolean set(String _key, Object _value, Integer _hashCode) {
        return defaultTargetClient.set(_key, _value, _hashCode);
    }


    public boolean set(String _key, Object _value, Long _expiry, Integer _hashCode) {
        return defaultTargetClient.set(_key, _value, _expiry, _hashCode);
    }


    public boolean set(String _key, Object _value, Long _expiry) {
        return defaultTargetClient.set(_key, _value, _expiry);
    }


    public boolean set(String _key, Object _value) {
        return defaultTargetClient.set(_key, _value);
    }


    public void setHashAlgorithm(HashAlgorithm ha) {
        defaultTargetClient.setHashAlgorithm(ha);
    }


    public void shutdown() {
        defaultTargetClient.shutdown();
    }


    public void shutdown(long _timeout, TimeUnit _unit) {
        defaultTargetClient.shutdown(_timeout, _unit);
    }


    public boolean storeCounter(String _key, Long _counter, Integer _hashCode) {
        return defaultTargetClient.storeCounter(_key, _counter, _hashCode);
    }


    public boolean storeCounter(String _key, Long _counter, Long _expiry, Integer _hashCode) {
        return defaultTargetClient.storeCounter(_key, _counter, _expiry, _hashCode);
    }


    public boolean storeCounter(String _key, Long _counter) {
        return defaultTargetClient.storeCounter(_key, _counter);
    }


    public int getInnerClientCount() {
        return defaultTargetClient.getInnerClientCount();
    }

    public void setAddrs(String addrs) {
        this.addrs = addrs;
    }
}
