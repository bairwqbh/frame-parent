package per.cby.frame.redis.storage.hash;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import per.cby.frame.redis.storage.AbstractRedisBaseStorage;
import per.cby.frame.redis.storage.RedisComponent;

/**
 * Redis哈希类型Map组件
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@SuppressWarnings("unchecked")
public class RedisHash<K, V> extends RedisComponent<K, V> implements Map<K, V> {

    /** Redis哈希存储容器 */
    private final DefaultRedisHashStorage<K, V> storage;

    /**
     * 构建容器
     * 
     * @param key        容器名称
     * @param keyClass   键类型
     * @param valueClass 值类型
     */
    public RedisHash(String name, Class<K> keyClass, Class<V> valueClass) {
        storage = new DefaultRedisHashStorage<K, V>(name, keyClass, valueClass);
    }

    @Override
    public AbstractRedisBaseStorage<K, V> storage() {
        return storage;
    }

    @Override
    public int size() {
        return (int) storage.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return storage.has((K) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return storage.getMap().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return storage.get((K) key);
    }

    @Override
    public V put(K key, V value) {
        storage.put(key, value);
        return value;
    }

    @Override
    public V remove(Object key) {
        V v = get(key);
        storage.delete((K) key);
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        storage.putAll(m);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Set<K> keySet() {
        return storage.keys();
    }

    @Override
    public Collection<V> values() {
        return storage.values();
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return storage.getMap().entrySet();
    }

}
