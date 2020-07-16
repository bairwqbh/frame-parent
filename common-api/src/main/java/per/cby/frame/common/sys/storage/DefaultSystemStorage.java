package per.cby.frame.common.sys.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 默认系统存储实现类
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings("unchecked")
public class DefaultSystemStorage implements SystemStorage {

    /** 默认系统存储容器 */
    private final Map<Object, Object> storage = new ConcurrentHashMap<Object, Object>();

    @Override
    public <K> boolean has(K key) {
        return storage.containsKey(key);
    }

    @Override
    public <K, V> V get(K key) {
        return (V) storage.get(key);
    }

    @Override
    public <K, V> V set(K key, V value) {
        return (V) storage.put(key, value);
    }

    @Override
    public <K, V> void setMap(Map<? extends K, ? extends V> map) {
        storage.putAll(map);
    }

    @Override
    public <K, V> V replace(K key, V value) {
        return (V) storage.replace(key, value);
    }

    @Override
    public <K, V> V remove(K key) {
        return (V) storage.remove(key);
    }

    @Override
    public <K, V> V getOrSet(K key, Supplier<V> supplier) {
        if (!has(key) || get(key) == null) {
            set(key, supplier.get());
        }
        return get(key);
    }

}
