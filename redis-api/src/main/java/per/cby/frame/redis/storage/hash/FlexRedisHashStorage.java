package per.cby.frame.redis.storage.hash;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import per.cby.frame.redis.storage.RedisContainerStorage;

/**
 * 灵活的Redis哈希存储接口
 * 
 * @author chenboyang
 * 
 * @param <K> 键类型
 * @param <V> 值类型
 */
public interface FlexRedisHashStorage<K, V> extends RedisContainerStorage {

    /**
     * 哈希键是否存在
     * 
     * @param key 容器键
     * @param hashKey 哈希键
     * @return 是否存在
     */
    boolean has(String key, K hashKey);

    /**
     * 放入哈希键值
     * 
     * @param key 容器键
     * @param hashKey 哈希键
     * @param value   哈希值
     */
    void put(String key, K hashKey, V value);

    /**
     * 放入一组哈希键值
     * 
     * @param key 容器键
     * @param map 哈希容器
     */
    void putAll(String key, Map<? extends K, ? extends V> map);

    /**
     * 当键不存在时，设置键的值
     * 
     * @param key 容器键
     * @param hashKey 哈希键
     * @param value   希键值
     * @return 是否设置成功
     */
    boolean putIfAbsent(String key, K hashKey, V value);

    /**
     * 删除一个哈希
     * 
     * @param key     容器键
     * @param hashKey 哈希键
     * @return 操作结果数据
     */
    long delete(String key, K hashKey);

    /**
     * 删除多个哈希
     * 
     * @param key      容器键
     * @param hashKeys 哈希键集合
     * @return 操作结果数据
     */
    long delete(String key, Collection<? extends K> hashKeys);

    /**
     * 根据值删除元素
     * 
     * @param key   容器键
     * @param value 值
     * @return 操作结果数据
     */
    long deleteByValue(String key, V value);

    /**
     * 获取一个哈希值
     * 
     * @param key 容器键
     * @param hashKey 哈希键
     * @return 哈希值
     */
    V get(String key, K hashKey);

    /**
     * 获取一组哈希值
     * 
     * @param key 容器键
     * @param hashKeys 哈希键集合
     * @return 哈希值集合
     */
    List<V> multiGet(String key, Collection<? extends K> hashKeys);

    /**
     * 根据键设置值类型为整形的增量值
     * 
     * @param key 容器键
     * @param hashKey 键
     * @param delta   增量值
     * @return 设置后增量值的值
     */
    long increment(String key, K hashKey, long delta);

    /**
     * 根据键设置值类型为浮点形的增量值
     * 
     * @param key 容器键
     * @param hashKey 键
     * @param delta   增量值
     * @return 设置后增量值的值
     */
    double increment(String key, K hashKey, double delta);

    /**
     * 获取容器哈希数量
     * 
     * @param key 容器键
     * @return 数量
     */
    long size(String key);

    /**
     * 获取整个哈希容器
     * 
     * @param key 容器键
     * @return 哈希容器
     */
    Map<K, V> getMap(String key);

    /**
     * 获取全部键
     * 
     * @param key 容器键
     * @return 哈希键集合
     */
    Set<K> keys(String key);

    /**
     * 获取全部值
     * 
     * @param key 容器键
     * @return 哈希值集合
     */
    List<V> values(String key);

}
