package per.cby.frame.redis.handler;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import per.cby.frame.redis.operate.HashOperate;
import per.cby.frame.redis.operate.ListOperate;
import per.cby.frame.redis.operate.ValueOperate;

import lombok.NoArgsConstructor;

/**
 * <h1>Redis存储处理类</h1>
 * <p>
 * Redis支持五种数据类型：string（字符串），hash（哈希），list（列表），set（集合）及zset（sorted set：有序集合）。
 * </p>
 * <p>
 * 五种类型分别有对应的处理对象
 * </p>
 * <p>
 * string（字符串）的处理对象为类的opsForValue()方法返回对象
 * </p>
 * <p>
 * hash（哈希）的处理对象为类的opsForHash()方法返回对象
 * </p>
 * <p>
 * list（列表）的处理对象为类的opsForList()方法返回对象
 * </p>
 * <p>
 * set（集合）的处理对象为类的opsForSet()方法返回对象
 * </p>
 * <p>
 * zset（有序集合）的处理对象为类的opsForZSet()方法返回对象
 * </p>
 * 
 * @author chenboyang
 * 
 * @param <K> 键类型
 * @param <V> 值类型
 */
@NoArgsConstructor
@SuppressWarnings("unchecked")
public class RedisHandler<K, V> extends RedisTemplate<K, V>
        implements ValueOperate<K, V>, HashOperate<K, V>, ListOperate<K, V> {

    /**
     * 构建处理器
     * 
     * @param connectionFactory 连接工厂
     */
    public RedisHandler(RedisConnectionFactory connectionFactory) {
        setConnectionFactory(connectionFactory);
    }

    // ==============================字符串操作==============================

    @Override
    public V valueGet(Object key) {
        return opsForValue().get(key);
    }

    @Override
    public List<V> valueMultiGet(Collection<K> key) {
        return opsForValue().multiGet(key);
    }

    @Override
    public void valueMultiSet(Map<? extends K, ? extends V> key) {
        opsForValue().multiSet(key);
    }

    @Override
    public void valueSet(K key, V value) {
        opsForValue().set(key, value);
    }

    @Override
    public void valueSet(K key, V value, long timeout, TimeUnit unit) {
        opsForValue().set(key, value, timeout, unit);
    }

    // ==============================哈希操作==============================

    @Override
    public Long hashDelete(K key, K... hashKeys) {
        return opsForHash().delete(key, hashKeys);
    }

    @Override
    public Boolean hashHasKey(K key, K hashKey) {
        return opsForHash().hasKey(key, hashKey);
    }

    @Override
    public V hashGet(K key, K hashKey) {
        return (V) opsForHash().get(key, hashKey);
    }

    @Override
    public List<V> hashMultiGet(K key, Collection<Object> hashKeys) {
        return (List<V>) opsForHash().multiGet(key, hashKeys);
    }

    @Override
    public void hashPutAll(K key, Map<? extends K, ? extends V> m) {
        opsForHash().putAll(key, m);
    }

    @Override
    public void hashPut(K key, K hashKey, V value) {
        opsForHash().put(key, hashKey, value);
    }

    @Override
    public List<V> hashValues(K key) {
        return (List<V>) opsForHash().values(key);
    }

    @Override
    public Map<K, V> hashEntries(K key) {
        return (Map<K, V>) opsForHash().entries(key);
    }

    // ==============================列表操作==============================

    @Override
    public List<V> listGet(K key) {
        return listRange(key, 0, opsForList().size(key) - 1);
    }

    @Override
    public List<V> listRange(K key, long start, long end) {
        return opsForList().range(key, start, end);
    }

    @Override
    public void listTrim(K key, long start, long end) {
        opsForList().trim(key, start, end);
    }

    @Override
    public Long listLeftPush(K key, V value) {
        return opsForList().leftPush(key, value);
    }

    @Override
    public Long listLeftPushAll(K key, V... values) {
        return opsForList().leftPushAll(key, values);
    }

    @Override
    public Long listLeftPushAll(K key, Collection<V> values) {
        return opsForList().leftPushAll(key, values);
    }

    @Override
    public Long listLeftPush(K key, V pivot, V value) {
        return opsForList().leftPush(key, pivot, value);
    }

    @Override
    public Long listRightPush(K key, V value) {
        return opsForList().rightPush(key, value);
    }

    @Override
    public Long listRightPushAll(K key, V... values) {
        return opsForList().rightPushAll(key, values);
    }

    @Override
    public Long listRightPushAll(K key, Collection<V> values) {
        return opsForList().rightPushAll(key, values);
    }

    @Override
    public Long listRightPush(K key, V pivot, V value) {
        return opsForList().rightPush(key, pivot, value);
    }

    @Override
    public void listSet(K key, long index, V value) {
        opsForList().set(key, index, value);
    }

    @Override
    public Long listRemove(K key, V value) {
        return opsForList().remove(key, 0, value);
    }

    @Override
    public Long listRemove(K key, long i, V value) {
        return opsForList().remove(key, i, value);
    }

    @Override
    public V listIndex(K key, long index) {
        return opsForList().index(key, index);
    }

    @Override
    public V listLeftPop(K key) {
        return opsForList().leftPop(key);
    }

    @Override
    public V listRightPop(K key) {
        return opsForList().rightPop(key);
    }

}
