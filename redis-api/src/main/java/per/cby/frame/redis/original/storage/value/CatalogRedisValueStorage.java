package per.cby.frame.redis.original.storage.value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import per.cby.frame.redis.annotation.RedisStorage;

/**
 * Redis目录结构默认值存储接口
 * 
 * @author chenboyang
 *
 * @param <String> 键类型
 * @param <V>      值类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public interface CatalogRedisValueStorage<V> extends RedisValueStorage<String, V> {

    /** 分隔符 */
    String SEPARATOR = ":";

    /** 全匹配 */
    String ALL_MATCH = "*";

    /**
     * 获取键前缀
     * 
     * @return 键前缀
     */
    default String keyPrefix() {
        String prefix = Optional.ofNullable(getClass().getAnnotation(RedisStorage.class)).map(RedisStorage::value)
                .orElse(StringUtils.EMPTY);
        if (StringUtils.isBlank(prefix)) {
            prefix = getClass().getName();
        }
        if (!prefix.endsWith(SEPARATOR)) {
            prefix += SEPARATOR;
        }
        return prefix;
    }

    /**
     * 获取键模式
     * 
     * @return 键模式
     */
    default String keyPattern() {
        return keyPrefix() + ALL_MATCH;
    }

    /**
     * 组装存储键
     * 
     * @param key 业务键
     * @return 存储键
     */
    default String assembleKey(String key) {
        return keyPrefix() + key;
    }

    @Override
    default boolean has(String key) {
        return RedisValueStorage.super.has(assembleKey(key));
    }

    @Override
    default void set(String key, V value) {
        RedisValueStorage.super.set(assembleKey(key), value);
    }

    @Override
    default void set(String key, V value, long timeout, TimeUnit unit) {
        RedisValueStorage.super.set(assembleKey(key), value, timeout, unit);
    }

    @Override
    default boolean setIfAbsent(String key, V value) {
        return RedisValueStorage.super.setIfAbsent(assembleKey(key), value);
    }

    @Override
    default void multiSet(Map<? extends String, ? extends V> map) {
        if (MapUtils.isNotEmpty(map)) {
            try {
                Map<String, V> newMap = map.getClass().newInstance();
                map.forEach((k, v) -> newMap.put(assembleKey(k), v));
                RedisValueStorage.super.multiSet(newMap);
            } catch (InstantiationException | IllegalAccessException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }
    }

    @Override
    default boolean multiSetIfAbsent(Map<? extends String, ? extends V> map) {
        if (MapUtils.isNotEmpty(map)) {
            try {
                Map<String, V> newMap = map.getClass().newInstance();
                map.forEach((k, v) -> newMap.put(assembleKey(k), v));
                return RedisValueStorage.super.multiSetIfAbsent(newMap);
            } catch (InstantiationException | IllegalAccessException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }
        return false;
    }

    @Override
    default V get(String key) {
        return RedisValueStorage.super.get(assembleKey(key));
    }

    @Override
    default V getAndSet(String key, V value) {
        return RedisValueStorage.super.getAndSet(assembleKey(key), value);
    }

    @Override
    default List<V> multiGet(Collection<String> keys) {
        if (CollectionUtils.isNotEmpty(keys)) {
            keys.stream().map((k) -> assembleKey(k));
        }
        return RedisValueStorage.super.multiGet(keys);
    }

    @Override
    default long increment(String key, long delta) {
        return RedisValueStorage.super.increment(assembleKey(key), delta);
    }

    @Override
    default double increment(String key, double delta) {
        return RedisValueStorage.super.increment(assembleKey(key), delta);
    }

    @Override
    default int append(String key, String value) {
        return RedisValueStorage.super.append(assembleKey(key), value);
    }

    @Override
    default String get(String key, long start, long end) {
        return RedisValueStorage.super.get(assembleKey(key), start, end);
    }

    @Override
    default void set(String key, V value, long offset) {
        RedisValueStorage.super.set(assembleKey(key), value, offset);
    }

    @Override
    default long size(String key) {
        return RedisValueStorage.super.size(assembleKey(key));
    }

    @Override
    default boolean setBit(String key, long offset, boolean value) {
        return RedisValueStorage.super.setBit(assembleKey(key), offset, value);
    }

    @Override
    default boolean getBit(String key, long offset) {
        return RedisValueStorage.super.getBit(assembleKey(key), offset);
    }

    @Override
    default void delete(String key) {
        RedisValueStorage.super.delete(assembleKey(key));
    }

    @Override
    default void delete(Collection<String> keys) {
        if (CollectionUtils.isNotEmpty(keys)) {
            keys.stream().map((k) -> assembleKey(k));
        }
        RedisValueStorage.super.delete(keys);
    }

    /**
     * 清除整个容器
     */
    default void clear() {
        delete(RedisValueStorage.super.handler().keys(keyPattern()));
    }

}
