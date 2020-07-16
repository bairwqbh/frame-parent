package per.cby.frame.redis.storage.value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import per.cby.frame.redis.storage.RedisBaseStorage;

/**
 * Redis值存储接口
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public interface RedisValueStorage<K, V> extends RedisBaseStorage {

    /**
     * 检查键是否存在
     * 
     * @param key 键
     * @return 是否存在
     */
    boolean has(K key);

    /**
     * 设置指定键的值
     * 
     * @param key   键
     * @param value 值
     */
    void set(K key, V value);

    /**
     * 设置拥有过期时间的键值
     * 
     * @param key     键
     * @param value   值
     * @param timeout 时间数值
     * @param unit    时间单位
     */
    void set(K key, V value, long timeout, TimeUnit unit);

    /**
     * 只有在键不存在时设置键的值
     * 
     * @param key   键
     * @param value 值
     * @return 是否设置成功
     */
    boolean setIfAbsent(K key, V value);

    /**
     * 设置一组键值
     * 
     * @param map 键值容器
     */
    void multiSet(Map<? extends K, ? extends V> map);

    /**
     * 键不存在时，使用容器中提供的键值对将多个键设置为多个值
     * 
     * @param map 键值容器
     * @return 是否设置成功
     */
    boolean multiSetIfAbsent(Map<? extends K, ? extends V> map);

    /**
     * 获取指定键的值
     * 
     * @param key 键
     * @return 值
     */
    V get(K key);

    /**
     * 设置键的值并返回其旧值
     * 
     * @param key   键
     * @param value 新值
     * @return 旧值
     */
    V getAndSet(K key, V value);

    /**
     * 获取一组值
     * 
     * @param keys 键集合
     * @return 值集合
     */
    List<V> multiGet(Collection<? extends K> keys);

    /**
     * 将键所储存的值加上给定的增量值
     * 
     * @param key   键
     * @param delta 增量值
     * @return 加上指定的增量值之后的值
     */
    long increment(K key, long delta);

    /**
     * 将键所储存的值加上给定的增量值
     * 
     * @param key   键
     * @param delta 增量值
     * @return 加上指定的增量值之后的值
     */
    double increment(K key, double delta);

    /**
     * 追加值
     * 
     * @param key   键
     * @param value 值
     * @return 追加后的值长度
     */
    int append(K key, String value);

    /**
     * 获取存储在指定键中字符串的子字符串，字符串的截取范围由开始位置和结束位置两个偏移量决定
     * 
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return 字符串
     */
    String get(K key, long start, long end);

    /**
     * 用值覆写给定键所储存的字符串值，从偏移位开始
     * 
     * @param key    键
     * @param value  值
     * @param offset 偏移位
     */
    void set(K key, V value, long offset);

    /**
     * 获取值长度
     * 
     * @param key 键
     * @return 长度
     */
    long size(K key);

    /**
     * 对键所储存的字符串值设置或清除指定偏移位上的位
     * 
     * @param key    键
     * @param offset 偏移位
     * @param value  位
     * @return 指定偏移量原来储存的位
     */
    boolean setBit(K key, long offset, boolean value);

    /**
     * 对键所储存的字符串值，获取指定偏移位上的位
     * 
     * @param key    键
     * @param offset 偏移位
     * @return 偏移位上的位
     */
    boolean getBit(K key, long offset);

    /**
     * 根据键删除对象
     * 
     * @param key 键
     * @return 是否移除完成
     */
    boolean delete(K key);

    /**
     * 根据键集删除对象
     * 
     * @param keys 键集
     * @return 移除成功数量
     */
    long delete(Collection<? extends K> keys);

}
