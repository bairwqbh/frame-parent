package per.cby.frame.redis.storage.value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.collections.RedisCollectionFactoryBean.CollectionType;

import per.cby.frame.redis.storage.AbstractRedisContainerStorage;

/**
 * Redis值存储接口
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class DefaultRedisValueStorage<K, V> extends AbstractRedisContainerStorage<K, V>
        implements RedisValueStorage<K, V> {

    /**
     * 初始化Redis容器组件
     */
    public DefaultRedisValueStorage() {
        super(null, null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param key        容器名称
     * @param keyClass   键类型
     * @param valueClass 值类型
     */
    public DefaultRedisValueStorage(String name, Class<K> keyClass, Class<V> valueClass) {
        super(name, keyClass, valueClass);
    }

    /**
     * 获取值存储操作处理器
     * 
     * @return 值存储操作器
     */
    private ValueOperations<String, String> ops() {
        return template().opsForValue();
    }

    @Override
    public boolean has(K key) {
        return Optional.ofNullable(template().hasKey(keySerialize(key))).orElse(false);
    }

    @Override
    public void set(K key, V value) {
        ops().set(keySerialize(key), valueSerialize(value));
    }

    @Override
    public void set(K key, V value, long timeout, TimeUnit unit) {
        ops().set(keySerialize(key), valueSerialize(value), timeout, unit);
    }

    @Override
    public boolean setIfAbsent(K key, V value) {
        return Optional.ofNullable(ops().setIfAbsent(keySerialize(key), valueSerialize(value))).orElse(false);
    }

    @Override
    public void multiSet(Map<? extends K, ? extends V> map) {
        ops().multiSet(serialize(map));
    }

    @Override
    public boolean multiSetIfAbsent(Map<? extends K, ? extends V> map) {
        return Optional.ofNullable(ops().multiSetIfAbsent(serialize(map))).orElse(false);
    }

    @Override
    public V get(K key) {
        return valueDeserialize(ops().get(key));
    }

    @Override
    public V getAndSet(K key, V value) {
        return valueDeserialize(ops().getAndSet(keySerialize(key), valueSerialize(value)));
    }

    @Override
    public List<V> multiGet(Collection<? extends K> keys) {
        return valueDeserialize(ops().multiGet(keySerialize(keys, CollectionType.SET)), CollectionType.LIST).stream()
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public long increment(K key, long delta) {
        return Optional.ofNullable(ops().increment(keySerialize(key), delta)).orElse(0L);
    }

    @Override
    public double increment(K key, double delta) {
        return Optional.ofNullable(ops().increment(keySerialize(key), delta)).orElse(0D);
    }

    @Override
    public int append(K key, String value) {
        return Optional.ofNullable(ops().append(keySerialize(key), value)).orElse(0);
    }

    @Override
    public String get(K key, long start, long end) {
        return ops().get(keySerialize(key), start, end);
    }

    @Override
    public void set(K key, V value, long offset) {
        ops().set(keySerialize(key), valueSerialize(value), offset);
    }

    @Override
    public long size(K key) {
        return Optional.ofNullable(ops().size(keySerialize(key))).orElse(0L);
    }

    @Override
    public boolean setBit(K key, long offset, boolean value) {
        return Optional.ofNullable(ops().setBit(keySerialize(key), offset, value)).orElse(false);
    }

    @Override
    public boolean getBit(K key, long offset) {
        return Optional.ofNullable(ops().getBit(keySerialize(key), offset)).orElse(false);
    }

    @Override
    public boolean delete(K key) {
        return Optional.ofNullable(template().delete(keySerialize(key))).orElse(false);
    }

    @Override
    public long delete(Collection<? extends K> keys) {
        Collection<String> keyColl = keySerialize(keys, CollectionType.SET);
        if (CollectionUtils.isEmpty(keyColl)) {
            return 0L;
        }
        return Optional.ofNullable(template().delete(keyColl)).orElse(0L);
    }

}
