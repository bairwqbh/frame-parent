package per.cby.frame.redis.original.storage.value;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;

import per.cby.frame.redis.handler.RedisHandler;
import per.cby.frame.redis.original.storage.RedisComponent;

/**
 * Redis集合组件
 * 
 * @author chenboyang
 *
 * @param <String> 键类型
 * @param <V>      值类型
 */
@Deprecated
public class RedisValue<V> extends RedisComponent<String, V> implements Map<String, V> {

    /** 数据存储是否过期 */
    private boolean timeoutble = false;

    /** 存储过期时间长度 */
    private long timeout = 30L;

    /** 存储过期时间单位 */
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    /** Redis值存储容器 */
    private SimpleRedisValueStorage<V> redisValueStorage = null;

    /**
     * 构建方法
     * 
     * @param name 容器名称
     */
    public RedisValue(String name) {
        this.name = name;
        init();
    }

    /**
     * 构建方法
     * 
     * @param name    容器名称
     * @param handler Redis存储操作处理器
     */
    public RedisValue(String name, RedisHandler<String, V> handler) {
        this.name = name;
        this.redisHandler = handler;
    }

    /**
     * 构建方法
     * 
     * @param name       容器名称
     * @param timeoutble 数据存储是否过期
     */
    public RedisValue(String name, boolean timeoutble) {
        this.name = name;
        this.timeoutble = timeoutble;
        init();
    }

    /**
     * 构建方法
     * 
     * @param name     容器名称
     * @param timeout  存储过期时间长度
     * @param timeUnit 存储过期时间单位
     */
    public RedisValue(String name, long timeout, TimeUnit timeUnit) {
        this.name = name;
        this.timeoutble = true;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        init();
    }

    /**
     * 构建方法
     * 
     * @param name       容器名称
     * @param handler    Redis存储操作处理器
     * @param timeoutble 数据存储是否过期
     */
    public RedisValue(String name, RedisHandler<String, V> handler, boolean timeoutble) {
        this.name = name;
        this.redisHandler = handler;
        this.timeoutble = timeoutble;
        init();
    }

    /**
     * 构建方法
     * 
     * @param name     容器名称
     * @param handler  Redis存储操作处理器
     * @param timeout  存储过期时间长度
     * @param timeUnit 存储过期时间单位
     */
    public RedisValue(String name, RedisHandler<String, V> handler, long timeout, TimeUnit timeUnit) {
        this.name = name;
        this.redisHandler = handler;
        this.timeoutble = true;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        redisValueStorage = new SimpleRedisValueStorage<V>(componentName());
        redisValueStorage.setRedisHandler(redisHandler);
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
        return redisValueStorage.has((String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public V get(Object key) {
        return redisValueStorage.get((String) key);
    }

    @Override
    public V put(String key, V value) {
        if (timeoutble) {
            redisValueStorage.set(key, value, timeout, timeUnit);
        } else {
            redisValueStorage.set(key, value);
        }
        return value;
    }

    @Override
    public V remove(Object key) {
        V v = get(key);
        redisValueStorage.delete((String) key);
        return v;
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> map) {
        if (timeoutble) {
            if (MapUtils.isNotEmpty(map)) {
                map.forEach((k, v) -> put(k, v));
            }
        } else {
            redisValueStorage.multiSet(map);
        }
    }

    @Override
    public void clear() {
        redisValueStorage.clear();
    }

    @Override
    public Set<String> keySet() {
        return redisValueStorage.handler().keys(redisValueStorage.keyPattern());
    }

    @Override
    public Collection<V> values() {
        return redisValueStorage.multiGet(keySet());
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return keySet().stream().collect(Collectors.toMap(Function.identity(), this::get)).entrySet();
    }

}
