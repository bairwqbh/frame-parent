package per.cby.frame.redis.storage.hash;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis哈希存储接口抽象实现
 * 
 * @author chenboyang
 * 
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class DefaultRedisHashStorage<K, V> extends AbstractRedisHashStorage<K, V> implements RedisHashStorage<K, V> {

    /**
     * 初始化Redis容器组件
     */
    public DefaultRedisHashStorage() {
        super(null, null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param key        容器名称
     * @param keyClass   键类型
     * @param valueClass 值类型
     */
    public DefaultRedisHashStorage(String key, Class<K> keyClass, Class<V> valueClass) {
        super(key, keyClass, valueClass);
    }

    @Override
    public boolean has(K hashKey) {
        return super.has(key(), hashKey);
    }

    @Override
    public void put(K hashKey, V value) {
        super.put(key(), hashKey, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        super.putAll(key(), map);
    }

    @Override
    public boolean putIfAbsent(K hashKey, V value) {
        return super.putIfAbsent(key(), hashKey, value);
    }

    @Override
    public long delete(K hashKey) {
        return super.delete(key(), hashKey);
    }

    @Override
    public long delete(Collection<? extends K> hashKeys) {
        return super.delete(key(), hashKeys);
    }

    @Override
    public long deleteByValue(V value) {
        return super.deleteByValue(key(), value);
    }

    @Override
    public V get(K hashKey) {
        return super.get(key(), hashKey);
    }

    @Override
    public List<V> multiGet(Collection<? extends K> hashKeys) {
        return super.multiGet(key(), hashKeys);
    }

    @Override
    public long increment(K hashKey, long delta) {
        return super.increment(key(), hashKey, delta);
    }

    @Override
    public double increment(K hashKey, double delta) {
        return super.increment(key(), hashKey, delta);
    }

    @Override
    public long size() {
        return super.size(key());
    }

    @Override
    public Map<K, V> getMap() {
        return super.getMap(key());
    }

    @Override
    public Set<K> keys() {
        return super.keys(key());
    }

    @Override
    public List<V> values() {
        return super.values(key());
    }

}
