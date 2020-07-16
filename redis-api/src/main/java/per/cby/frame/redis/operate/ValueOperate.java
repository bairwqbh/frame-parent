package per.cby.frame.redis.operate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis字符值操作接口
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public interface ValueOperate<K, V> {

    /**
     * 获取值
     * 
     * @param key 键
     * @return 值
     */
    V valueGet(Object key);

    /**
     * 获取值列表
     * 
     * @param key 键集合
     * @return 值列表
     */
    List<V> valueMultiGet(Collection<K> key);

    /**
     * 插入值列表
     * 
     * @param key 键值集合
     */
    void valueMultiSet(Map<? extends K, ? extends V> key);

    /**
     * 插入值
     * 
     * @param key   键
     * @param value 值
     */
    void valueSet(K key, V value);

    /**
     * 插入值及设置超时时间
     * 
     * @param key     键
     * @param value   值
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    void valueSet(K key, V value, long timeout, TimeUnit unit);

}
