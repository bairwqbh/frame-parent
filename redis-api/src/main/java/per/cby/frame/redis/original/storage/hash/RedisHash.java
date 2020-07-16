package per.cby.frame.redis.original.storage.hash;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import per.cby.frame.redis.handler.RedisHandler;
import per.cby.frame.redis.original.storage.RedisComponent;

/**
 * Redis哈希类型Map组件
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public class RedisHash<K, V> extends RedisComponent<String, V> implements Map<K, V> {

    /**
     * 构建方法
     * 
     * @param name 容器名称
     */
    public RedisHash(String name) {
        this.name = name;
    }

    /**
     * 构建方法
     * 
     * @param name    容器名称
     * @param handler Redis存储操作处理器
     */
    public RedisHash(String name, RedisHandler<String, V> handler) {
        this.name = name;
        this.redisHandler = handler;
    }

    /**
     * Redis哈希存储容器
     */
    private RedisHashStorage<K, V> redisHashStorage = new RedisHashStorage<K, V>() {
        @Override
        public String key() {
            return componentName();
        }

        @Override
        public RedisHandler<String, V> handler() {
            return getRedisHandler();
        }
    };

    @Override
    public int size() {
        return (int) redisHashStorage.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return redisHashStorage.has((K) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return redisHashStorage.getMap().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return redisHashStorage.get((K) key);
    }

    @Override
    public V put(K key, V value) {
        redisHashStorage.put((K) key, (V) value);
        return value;
    }

    @Override
    public V remove(Object key) {
        V v = get(key);
        redisHashStorage.delete((K) key);
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        redisHashStorage.putAll(m);
    }

    @Override
    public void clear() {
        redisHashStorage.clear();
    }

    @Override
    public Set<K> keySet() {
        return redisHashStorage.keys();
    }

    @Override
    public Collection<V> values() {
        return redisHashStorage.values();
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return redisHashStorage.getMap().entrySet();
    }

}
