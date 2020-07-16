package per.cby.frame.common.sys.storage;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.apache.commons.collections4.MapUtils;

/**
 * 软引用系统存储
 * 
 * @author chenboyang
 * @since 2019年5月30日
 *
 */
@SuppressWarnings("unchecked")
public class SoftSystemStorage implements SystemStorage {

    /** 存储容器 */
    private final Map<Object, SoftReference<Object>> storage = new ConcurrentHashMap<Object, SoftReference<Object>>();

    @Override
    public <K> boolean has(K key) {
        return storage.containsKey(key);
    }

    @Override
    public <K, V> V get(K key) {
        return (V) Optional.ofNullable(storage.get(key)).map(SoftReference::get).orElse(null);
    }

    @Override
    public <K, V> V set(K key, V value) {
        return (V) Optional.ofNullable(storage.put(key, new SoftReference<Object>(value))).map(SoftReference::get)
                .orElse(null);
    }

    @Override
    public <K, V> void setMap(Map<? extends K, ? extends V> map) {
        if (MapUtils.isNotEmpty(map)) {
            map.forEach((k, v) -> set(k, v));
        }
    }

    @Override
    public <K, V> V replace(K key, V value) {
        return (V) Optional.ofNullable(storage.replace(key, new SoftReference<Object>(value))).map(SoftReference::get)
                .orElse(null);
    }

    @Override
    public <K, V> V remove(K key) {
        return (V) Optional.ofNullable(storage.remove(key)).map(SoftReference::get).orElse(null);
    }

    @Override
    public <K, V> V getOrSet(K key, Supplier<V> supplier) {
        if (!has(key) || get(key) == null) {
            set(key, supplier.get());
        }
        return get(key);
    }

}
