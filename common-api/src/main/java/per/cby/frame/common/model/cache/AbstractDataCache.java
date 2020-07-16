package per.cby.frame.common.model.cache;

import java.util.concurrent.TimeUnit;

import per.cby.frame.common.model.time.TimeMap;

/**
 * 数据缓存接口抽象类
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 *
 */
public abstract class AbstractDataCache<K, V> implements DataCache<K, V> {

    @Override
    public boolean has(K key) {
        return timeMap().containsKey(key);
    }

    @Override
    public V get(K key) {
        return timeMap().get(key);
    }

    @Override
    public void set(K key, V value, long timeout, TimeUnit timeUnit) {
        timeMap().put(key, value, timeout, timeUnit);
    }

    @Override
    public void remove(K key) {
        timeMap().remove(key);
    }

    @Override
    public void clear() {
        timeMap().clear();
    }

    /**
     * 获取数据缓存存储容器
     * 
     * @return 存储容器
     */
    protected abstract TimeMap<K, V> timeMap();

}
