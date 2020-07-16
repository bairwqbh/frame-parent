package per.cby.frame.redis.original.storage.hash;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;

import per.cby.frame.redis.original.storage.RedisContainerStorage;

/**
 * Redis哈希存储接口
 * 
 * @author chenboyang
 * 
 * @param <K> 哈希键类型
 * @param <V> 哈希值类型
 */
@Deprecated
public interface RedisHashStorage<K, V> extends RedisContainerStorage<String, V> {

    /**
     * 获取哈希操作器
     * 
     * @return 哈希操作器
     */
    default HashOperations<String, K, V> ops() {
        return handler().opsForHash();
    }

    /**
     * 哈希键是否存在
     * 
     * @param hashKey 哈希键
     * @return 是否存在
     */
    default boolean has(K hashKey) {
        return Optional.ofNullable(ops().hasKey(key(), hashKey)).orElse(false);
    }

    /**
     * 放入哈希键值
     * 
     * @param hashKey 哈希键
     * @param value   哈希值
     */
    default void put(K hashKey, V value) {
        ops().put(key(), hashKey, value);
    }

    /**
     * 放入一组哈希键值
     * 
     * @param map 哈希容器
     */
    default void putAll(Map<? extends K, ? extends V> map) {
        ops().putAll(key(), map);
    }

    /**
     * 当键不存在时，设置键的值
     * 
     * @param hashKey 哈希键
     * @param value   希键值
     * @return 是否设置成功
     */
    default boolean putIfAbsent(K hashKey, V value) {
        return Optional.ofNullable(ops().putIfAbsent(key(), hashKey, value)).orElse(false);
    }

    /**
     * 删除一个哈希
     * 
     * @param hashKey 哈希键
     */
    default long delete(K hashKey) {
        return Optional.ofNullable(ops().delete(key(), hashKey)).orElse(0L);
    }

    /**
     * 删除多个哈希
     * 
     * @param hashKeys 哈希键集合
     */
    default long delete(Collection<K> hashKeys) {
        return Optional.ofNullable(ops().delete(key(), hashKeys.toArray())).orElse(0L);
    }

    /**
     * 获取一个哈希值
     * 
     * @param hashKey 哈希键
     * @return 哈希值
     */
    default V get(K hashKey) {
        return ops().get(key(), hashKey);
    }

    /**
     * 获取一组哈希值
     * 
     * @param hashKeys 哈希键集合
     * @return 哈希值集合
     */
    default List<V> multiGet(Collection<K> hashKeys) {
        return ops().multiGet(key(), hashKeys);
    }

    /**
     * 根据键设置值类型为整形的增量值
     * 
     * @param hashKey 键
     * @param delta   增量值
     * @return 设置后增量值的值
     */
    default long increment(K hashKey, long delta) {
        return Optional.ofNullable(ops().increment(key(), hashKey, delta)).orElse(0L);
    }

    /**
     * 根据键设置值类型为浮点形的增量值
     * 
     * @param hashKey 键
     * @param delta   增量值
     * @return 设置后增量值的值
     */
    default double increment(K hashKey, double delta) {
        return Optional.ofNullable(ops().increment(key(), hashKey, delta)).orElse(0D);
    }

    /**
     * 获取容器哈希数量
     * 
     * @return 数量
     */
    default long size() {
        return Optional.ofNullable(ops().size(key())).orElse(0L);
    }

    /**
     * 获取整个哈希容器
     * 
     * @return 哈希容器
     */
    default Map<K, V> getMap() {
        return ops().entries(key());
    }

    /**
     * 获取全部键
     * 
     * @return 哈希键集合
     */
    default Set<K> keys() {
        return ops().keys(key());
    }

    /**
     * 获取全部值
     * 
     * @return 哈希值集合
     */
    default List<V> values() {
        return ops().values(key());
    }

    /**
     * 扫描元素
     * 
     * @param options 扫描配置项
     * @return 元素光标
     */
    default Cursor<Entry<K, V>> scan(ScanOptions options) {
        return ops().scan(key(), options);
    }

}
