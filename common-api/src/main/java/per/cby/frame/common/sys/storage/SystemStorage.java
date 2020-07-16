package per.cby.frame.common.sys.storage;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 系统存储接口
 * 
 * @author chenboyang
 *
 */
public interface SystemStorage {

    /**
     * 根据键判断是否存在
     * 
     * @param key 键
     * @return 是否存在
     */
    <K> boolean has(K key);

    /**
     * 根据键获取对象
     * 
     * @param key 键
     * @return 对象
     */
    <K, V> V get(K key);

    /**
     * 将键值对象设置到存储中
     * 
     * @param key   键
     * @param value 值
     * @return 旧值
     */
    <K, V> V set(K key, V value);

    /**
     * 将MAP数据设置到存储中
     * 
     * @param map MAP数据
     */
    <K, V> void setMap(Map<? extends K, ? extends V> map);

    /**
     * 根据键替换新值
     * 
     * @param key   键
     * @param value 值
     * @return 旧值
     */
    <K, V> V replace(K key, V value);

    /**
     * 根据键移除对象
     * 
     * @param key 键
     * @return 旧值
     */
    <K, V> V remove(K key);

    /**
     * 根据键获取对象或设置对象
     * 
     * @param key      键
     * @param supplier 值获取函数
     * @return 对象
     */
    <K, V> V getOrSet(K key, Supplier<V> supplier);

}
