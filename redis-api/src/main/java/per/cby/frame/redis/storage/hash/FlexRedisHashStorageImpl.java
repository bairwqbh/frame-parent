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
public class FlexRedisHashStorageImpl<K, V> extends AbstractRedisHashStorage<K, V>
        implements FlexRedisHashStorage<K, V> {

    /**
     * 初始化Redis容器组件
     */
    public FlexRedisHashStorageImpl() {
        super(null, null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param key        容器名称
     * @param keyClass   键类型
     * @param valueClass 值类型
     */
    public FlexRedisHashStorageImpl(String key, Class<K> keyClass, Class<V> valueClass) {
        super(key, keyClass, valueClass);
    }

    @Override
    public boolean has(String key, K hashKey) {
        return super.has(key(key), hashKey);
    }

    @Override
    public void put(String key, K hashKey, V value) {
        super.put(key(key), hashKey, value);
    }

    @Override
    public void putAll(String key, Map<? extends K, ? extends V> map) {
        super.putAll(key(key), map);
    }

    @Override
    public boolean putIfAbsent(String key, K hashKey, V value) {
        return super.putIfAbsent(key(key), hashKey, value);
    }

    @Override
    public long delete(String key, K hashKey) {
        return super.delete(key(key), hashKey);
    }

    @Override
    public long delete(String key, Collection<? extends K> hashKeys) {
        return super.delete(key(key), hashKeys);
    }

    @Override
    public long deleteByValue(String key, V value) {
        return super.deleteByValue(key(key), value);
    }

    @Override
    public V get(String key, K hashKey) {
        return super.get(key(key), hashKey);
    }

    @Override
    public List<V> multiGet(String key, Collection<? extends K> hashKeys) {
        return super.multiGet(key(key), hashKeys);
    }

    @Override
    public long increment(String key, K hashKey, long delta) {
        return super.increment(key(key), hashKey, delta);
    }

    @Override
    public double increment(String key, K hashKey, double delta) {
        return super.increment(key(key), hashKey, delta);
    }

    @Override
    public long size(String key) {
        return super.size(key(key));
    }

    @Override
    public Map<K, V> getMap(String key) {
        return super.getMap(key(key));
    }

    @Override
    public Set<K> keys(String key) {
        return super.keys(key(key));
    }

    @Override
    public List<V> values(String key) {
        return super.values(key(key));
    }

}
