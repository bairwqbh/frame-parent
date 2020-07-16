package per.cby.frame.redis.storage.hash;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.support.collections.RedisCollectionFactoryBean.CollectionType;

import per.cby.frame.redis.storage.AbstractRedisContainerStorage;

/**
 * Redis哈希存储接口抽象实现
 * 
 * @author chenboyang
 * 
 * @param <K> 键类型
 * @param <V> 值类型
 */
public abstract class AbstractRedisHashStorage<K, V> extends AbstractRedisContainerStorage<K, V>
        implements FlexRedisHashStorage<K, V> {

    /**
     * 初始化Redis容器组件
     */
    public AbstractRedisHashStorage() {
        super(null, null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param key        容器名称
     * @param keyClass   键类型
     * @param valueClass 值类型
     */
    public AbstractRedisHashStorage(String key, Class<K> keyClass, Class<V> valueClass) {
        super(key, keyClass, valueClass);
    }

    /**
     * 获取哈希操作器
     * 
     * @return 哈希操作器
     */
    public HashOperations<String, String, String> ops() {
        return template().opsForHash();
    }

    @Override
    public boolean has(String key, K hashKey) {
        return Optional.ofNullable(ops().hasKey(key, keySerialize(hashKey))).orElse(false);
    }

    @Override
    public void put(String key, K hashKey, V value) {
        ops().put(key, keySerialize(hashKey), valueSerialize(value));
    }

    @Override
    public void putAll(String key, Map<? extends K, ? extends V> map) {
        ops().putAll(key, serialize(map));
    }

    @Override
    public boolean putIfAbsent(String key, K hashKey, V value) {
        return Optional.ofNullable(ops().putIfAbsent(key, keySerialize(hashKey), valueSerialize(value))).orElse(false);
    }

    @Override
    public long delete(String key, K hashKey) {
        return Optional.ofNullable(ops().delete(key, keySerialize(hashKey))).orElse(0L);
    }

    @Override
    public long delete(String key, Collection<? extends K> hashKeys) {
        return Optional.ofNullable(ops().delete(key, keySerialize(hashKeys, CollectionType.SET).toArray())).orElse(0L);
    }

    @Override
    public long deleteByValue(String key, V value) {
        if (value == null) {
            return 0;
        }
        Map<K, V> map = getMap(key);
        if (MapUtils.isEmpty(map)) {
            return 0;
        }
        List<K> keys = map.entrySet().stream().filter(t -> value.equals(t.getValue())).map(Entry::getKey)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(keys)) {
            return 0;
        }
        return delete(key, keys);
    }

    @Override
    public V get(String key, K hashKey) {
        return valueDeserialize(ops().get(key, keySerialize(hashKey)));
    }

    @Override
    public List<V> multiGet(String key, Collection<? extends K> hashKeys) {
        return valueDeserialize(ops().multiGet(key, keySerialize(hashKeys, CollectionType.SET)), CollectionType.LIST)
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public long increment(String key, K hashKey, long delta) {
        return Optional.ofNullable(ops().increment(key, keySerialize(hashKey), delta)).orElse(0L);
    }

    @Override
    public double increment(String key, K hashKey, double delta) {
        return Optional.ofNullable(ops().increment(key, keySerialize(hashKey), delta)).orElse(0D);
    }

    @Override
    public long size(String key) {
        return Optional.ofNullable(ops().size(key)).orElse(0L);
    }

    @Override
    public Map<K, V> getMap(String key) {
        return deserialize(ops().entries(key));
    }

    @Override
    public Set<K> keys(String key) {
        return keyDeserialize(ops().keys(key), CollectionType.SET);
    }

    @Override
    public List<V> values(String key) {
        return valueDeserialize(ops().values(key), CollectionType.LIST).stream().filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
