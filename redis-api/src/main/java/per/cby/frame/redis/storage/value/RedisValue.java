package per.cby.frame.redis.storage.value;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;

import per.cby.frame.redis.storage.AbstractRedisBaseStorage;
import per.cby.frame.redis.storage.RedisComponent;

/**
 * Redis集合组件
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@SuppressWarnings("unchecked")
public class RedisValue<T> extends RedisComponent<String, T> implements Map<String, T> {

    /** 数据存储是否过期 */
    private boolean timeoutble = false;

    /** 存储过期时间长度 */
    private long timeout = 30L;

    /** 存储过期时间单位 */
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    /** Redis值存储容器 */
    private final DefaultCatalogRedisValueStorage<T> storage;

    /**
     * 构建方法
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public RedisValue(String name, Class<T> bizClass) {
        storage = new DefaultCatalogRedisValueStorage<T>(name, bizClass);
    }

    @Override
    public AbstractRedisBaseStorage<String, T> storage() {
        return storage;
    }

    /**
     * 构建方法
     * 
     * @param name       容器名称
     * @param bizClass   元素业务类型
     * @param timeoutble 数据存储是否过期
     */
    public RedisValue(String name, Class<T> bizClass, boolean timeoutble) {
        this.timeoutble = timeoutble;
        storage = new DefaultCatalogRedisValueStorage<T>(name, bizClass);
    }

    /**
     * 构建方法
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     * @param timeout  存储过期时间长度
     * @param timeUnit 存储过期时间单位
     */
    public RedisValue(String name, Class<T> bizClass, long timeout, TimeUnit timeUnit) {
        this.timeoutble = true;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        storage = new DefaultCatalogRedisValueStorage<T>(name, bizClass);
    }

    @Override
    public int size() {
        return keySet().size();
    }

    @Override
    public boolean isEmpty() {
        return keySet().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return storage.has((String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public T get(Object key) {
        return storage.get((String) key);
    }

    @Override
    public T put(String key, T value) {
        if (timeoutble) {
            storage.set(key, value, timeout, timeUnit);
        } else {
            storage.set(key, value);
        }
        return value;
    }

    @Override
    public T remove(Object key) {
        T v = get(key);
        storage.delete((String) key);
        return v;
    }

    @Override
    public void putAll(Map<? extends String, ? extends T> map) {
        if (timeoutble) {
            if (MapUtils.isNotEmpty(map)) {
                map.forEach((k, v) -> put(k, v));
            }
        } else {
            storage.multiSet((Map<String, ? extends T>) map);
        }
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Set<String> keySet() {
        return storage.template().keys(storage.keyPattern());
    }

    @Override
    public Collection<T> values() {
        return storage.multiGet(keySet());
    }

    @Override
    public Set<Entry<String, T>> entrySet() {
        return keySet().stream().collect(Collectors.toMap(Function.identity(), this::get)).entrySet();
    }

}
