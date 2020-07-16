package per.cby.frame.redis.original.storage.value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ValueOperations;

import per.cby.frame.redis.original.storage.RedisBaseStorage;

/**
 * Redis值存储接口
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@Deprecated
public interface RedisValueStorage<K, V> extends RedisBaseStorage<K, V> {

    /**
     * 获取哈希操作处理器
     * 
     * @return 哈希操作器
     */
    default ValueOperations<K, V> ops() {
        return handler().opsForValue();
    }

    /**
     * 检查键是否存在
     * 
     * @param key 键
     * @return 是否存在
     */
    default boolean has(K key) {
        return Optional.ofNullable(handler().hasKey(key)).orElse(false);
    }

    /**
     * 设置指定键的值
     * 
     * @param key   键
     * @param value 值
     */
    default void set(K key, V value) {
        ops().set(key, value);
    }

    /**
     * 设置拥有过期时间的键值
     * 
     * @param key     键
     * @param value   值
     * @param timeout 时间数值
     * @param unit    时间单位
     */
    default void set(K key, V value, long timeout, TimeUnit unit) {
        ops().set(key, value, timeout, unit);
    }

    /**
     * 只有在键不存在时设置键的值
     * 
     * @param key   键
     * @param value 值
     * @return 是否设置成功
     */
    default boolean setIfAbsent(K key, V value) {
        return Optional.ofNullable(ops().setIfAbsent(key, value)).orElse(false);
    }

    /**
     * 设置一组键值
     * 
     * @param map 键值容器
     */
    default void multiSet(Map<? extends K, ? extends V> map) {
        ops().multiSet(map);
    }

    /**
     * 键不存在时，使用容器中提供的键值对将多个键设置为多个值
     * 
     * @param map 键值容器
     * @return 是否设置成功
     */
    default boolean multiSetIfAbsent(Map<? extends K, ? extends V> map) {
        return Optional.ofNullable(ops().multiSetIfAbsent(map)).orElse(false);
    }

    /**
     * 获取指定键的值
     * 
     * @param key 键
     * @return 值
     */
    default V get(K key) {
        return ops().get(key);
    }

    /**
     * 设置键的值并返回其旧值
     * 
     * @param key   键
     * @param value 新值
     * @return 旧值
     */
    default V getAndSet(K key, V value) {
        return ops().getAndSet(key, value);
    }

    /**
     * 获取一组值
     * 
     * @param keys 键集合
     * @return 值集合
     */
    default List<V> multiGet(Collection<K> keys) {
        return ops().multiGet(keys);
    }

    /**
     * 将键所储存的值加上给定的增量值
     * 
     * @param key   键
     * @param delta 增量值
     * @return 加上指定的增量值之后的值
     */
    default long increment(K key, long delta) {
        return Optional.ofNullable(ops().increment(key, delta)).orElse(0L);
    }

    /**
     * 将键所储存的值加上给定的增量值
     * 
     * @param key   键
     * @param delta 增量值
     * @return 加上指定的增量值之后的值
     */
    default double increment(K key, double delta) {
        return Optional.ofNullable(ops().increment(key, delta)).orElse(0D);
    }

    /**
     * 追加值
     * 
     * @param key   键
     * @param value 值
     * @return 追加后的值长度
     */
    default int append(K key, String value) {
        return Optional.ofNullable(ops().append(key, value)).orElse(0);
    }

    /**
     * 获取存储在指定键中字符串的子字符串，字符串的截取范围由开始位置和结束位置两个偏移量决定
     * 
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return 字符串
     */
    default String get(K key, long start, long end) {
        return ops().get(key, start, end);
    }

    /**
     * 用值覆写给定键所储存的字符串值，从偏移位开始
     * 
     * @param key    键
     * @param value  值
     * @param offset 偏移位
     */
    default void set(K key, V value, long offset) {
        ops().set(key, value, offset);
    }

    /**
     * 获取值长度
     * 
     * @param key 键
     * @return 长度
     */
    default long size(K key) {
        return Optional.ofNullable(ops().size(key)).orElse(0L);
    }

    /**
     * 对键所储存的字符串值设置或清除指定偏移位上的位
     * 
     * @param key    键
     * @param offset 偏移位
     * @param value  位
     * @return 指定偏移量原来储存的位
     */
    default boolean setBit(K key, long offset, boolean value) {
        return Optional.ofNullable(ops().setBit(key, offset, value)).orElse(false);
    }

    /**
     * 对键所储存的字符串值，获取指定偏移位上的位
     * 
     * @param key    键
     * @param offset 偏移位
     * @return 偏移位上的位
     */
    default boolean getBit(K key, long offset) {
        return Optional.ofNullable(ops().getBit(key, offset)).orElse(false);
    }

    /**
     * 根据键删除对象
     * 
     * @param key 键
     */
    default void delete(K key) {
        handler().delete(key);
    }

    /**
     * 根据键集删除对象
     * 
     * @param keys 键集
     */
    default void delete(Collection<K> keys) {
        handler().delete(keys);
    }

}
