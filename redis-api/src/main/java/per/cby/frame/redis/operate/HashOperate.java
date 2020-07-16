package per.cby.frame.redis.operate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Redis哈希操作接口
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@SuppressWarnings("unchecked")
public interface HashOperate<K, V> {

    /**
     * 根据哈希键集删除哈希中的元素
     * 
     * @param key      键
     * @param hashKeys 哈希键集
     * @return 删除记录数
     */
    Long hashDelete(K key, K... hashKeys);

    /**
     * 键在哈希中是否存在
     * 
     * @param key     键
     * @param hashKey 哈希键
     * @return 是否存在
     */
    Boolean hashHasKey(K key, K hashKey);

    /**
     * 获取哈希中的值
     * 
     * @param key     键
     * @param hashKey 哈希键
     * @return 值
     */
    V hashGet(K key, K hashKey);

    /**
     * 获取多个哈希值
     * 
     * @param key      键
     * @param hashKeys 哈希键集
     * @return 哈希值列表
     */
    List<V> hashMultiGet(K key, Collection<Object> hashKeys);

    /**
     * 插入一个哈希集合
     * 
     * @param key 键
     * @param m   哈希集合
     */
    void hashPutAll(K key, Map<? extends K, ? extends V> m);

    /**
     * 插入一个哈希键值
     * 
     * @param key     键
     * @param hashKey 哈希键
     * @param value   哈希值
     */
    void hashPut(K key, K hashKey, V value);

    /**
     * 获取所有哈希值
     * 
     * @param key 键
     * @return 哈希值列表
     */
    List<V> hashValues(K key);

    /**
     * 获取整个哈希集合
     * 
     * @param key 键
     * @return 哈希集合
     */
    Map<K, V> hashEntries(K key);

}
