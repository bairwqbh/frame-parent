package per.cby.frame.redis.storage.value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis目录结构值存储
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@Slf4j
@SuppressWarnings("unchecked")
public class DefaultCatalogRedisValueStorage<T> extends DefaultRedisValueStorage<String, T>
        implements CatalogRedisValueStorage<T> {

    /** 分隔符 */
    protected static final String SEPARATOR = ":";

    /** 全匹配 */
    protected static final String ALL_MATCH = "*";

    /** 键前缀 */
    private String keyPrefix;

    /** 键模式 */
    private String keyPattern;

    /**
     * 初始化Redis容器组件
     */
    public DefaultCatalogRedisValueStorage() {
        this(null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public DefaultCatalogRedisValueStorage(String key, Class<T> bizClass) {
        super(key, String.class, bizClass);
    }

    /**
     * 获取键前缀
     * 
     * @return 键前缀
     */
    protected String keyPrefix() {
        if (keyPrefix == null) {
            keyPrefix = key();
        }
        if (StringUtils.isBlank(keyPrefix)) {
            keyPrefix = getClass().getName();
        }
        if (!keyPrefix.endsWith(SEPARATOR)) {
            keyPrefix += SEPARATOR;
        }
        return keyPrefix;
    }

    /**
     * 获取键模式
     * 
     * @return 键模式
     */
    protected String keyPattern() {
        if (keyPattern == null) {
            keyPattern = keyPrefix() + ALL_MATCH;
        }
        return keyPattern;
    }

    /**
     * 组装存储键
     * 
     * @param key 业务键
     * @return 存储键
     */
    public String assembleKey(String key) {
        return keyPrefix() + key;
    }

    @Override
    public boolean has(String key) {
        return super.has(assembleKey(key));
    }

    @Override
    public void set(String key, T value) {
        super.set(assembleKey(key), value);
    }

    @Override
    public void set(String key, T value, long timeout, TimeUnit unit) {
        super.set(assembleKey(key), value, timeout, unit);
    }

    @Override
    public boolean setIfAbsent(String key, T value) {
        return super.setIfAbsent(assembleKey(key), value);
    }

    @Override
    public void multiSet(Map<? extends String, ? extends T> map) {
        if (MapUtils.isNotEmpty(map)) {
            try {
                Map<String, T> newMap = map.getClass().newInstance();
                map.forEach((k, v) -> newMap.put(assembleKey(k), v));
                super.multiSet(newMap);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean multiSetIfAbsent(Map<? extends String, ? extends T> map) {
        if (MapUtils.isNotEmpty(map)) {
            try {
                Map<String, T> newMap = map.getClass().newInstance();
                map.forEach((k, v) -> newMap.put(assembleKey(k), v));
                return super.multiSetIfAbsent(newMap);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
        return false;
    }

    @Override
    public T get(String key) {
        return super.get(assembleKey(key));
    }

    @Override
    public T getAndSet(String key, T value) {
        return super.getAndSet(assembleKey(key), value);
    }

    @Override
    public List<T> multiGet(Collection<? extends String> keys) {
        if (CollectionUtils.isNotEmpty(keys)) {
            keys = keys.stream().map(this::assembleKey).collect(Collectors.toSet());
        }
        return super.multiGet(keys);
    }

    @Override
    public long increment(String key, long delta) {
        return super.increment(assembleKey(key), delta);
    }

    @Override
    public double increment(String key, double delta) {
        return super.increment(assembleKey(key), delta);
    }

    @Override
    public int append(String key, String value) {
        return super.append(assembleKey(key), value);
    }

    @Override
    public String get(String key, long start, long end) {
        return super.get(assembleKey(key), start, end);
    }

    @Override
    public void set(String key, T value, long offset) {
        super.set(assembleKey(key), value, offset);
    }

    @Override
    public long size(String key) {
        return super.size(assembleKey(key));
    }

    @Override
    public boolean setBit(String key, long offset, boolean value) {
        return super.setBit(assembleKey(key), offset, value);
    }

    @Override
    public boolean getBit(String key, long offset) {
        return super.getBit(assembleKey(key), offset);
    }

    @Override
    public boolean delete(String key) {
        return super.delete(assembleKey(key));
    }

    @Override
    public long delete(Collection<? extends String> keys) {
        if (CollectionUtils.isNotEmpty(keys)) {
            keys = keys.stream().map(this::assembleKey).collect(Collectors.toSet());
        }
        return super.delete(keys);
    }

    @Override
    public boolean clear() {
        return delete(keys()) > 0L;
    }

    @Override
    public Collection<String> keys() {
        return template().keys(keyPattern());
    }

}
