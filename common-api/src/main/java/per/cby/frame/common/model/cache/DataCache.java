package per.cby.frame.common.model.cache;

import java.util.concurrent.TimeUnit;

/**
 * 数据缓存接口
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public interface DataCache<K, V> {

    /**
     * 根据键判断是否存在
     * 
     * @param key 键
     * @return 是否存在
     */
    boolean has(K key);

    /**
     * 根据键获取对象
     * 
     * @param key 键
     * @return 对象
     */
    V get(K key);

    /**
     * 将键值对象设置到存储中
     * 
     * @param key     键
     * @param value   值
     * @param timeout 时间数值
     * @param unit    时间单位
     */
    void set(K key, V value, long timeout, TimeUnit unit);

    /**
     * 根据键移除对象
     * 
     * @param key 键
     */
    void remove(K key);

    /**
     * 清除全部缓存
     */
    void clear();

}
