package per.cby.frame.redis.storage;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import per.cby.frame.redis.annotation.RedisStorage;

import lombok.AccessLevel;
import lombok.Setter;

/**
 * Redis容器存储接口抽象实现
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public abstract class AbstractRedisContainerStorage<K, V> extends AbstractRedisBaseStorage<K, V>
        implements RedisContainerStorage {

    /** 容器键 */
    @Setter(AccessLevel.MODULE)
    private String key;

    /**
     * 初始化Redis容器组件
     * 
     * @param key        容器名称
     * @param keyClass   键类型
     * @param valueClass 值类型
     */
    protected AbstractRedisContainerStorage(String key, Class<K> keyClass, Class<V> valueClass) {
        setKey(key);
        setKeyClass(keyClass);
        setValueClass(valueClass);
    }

    @Override
    public String key() {
        return Optional.ofNullable(key)
                .orElseGet(() -> key = Optional.ofNullable(redisConf()).map(RedisStorage::value).orElse(StringUtils.EMPTY));
    }

    @Override
    public boolean clear() {
        return Optional.ofNullable(template().delete(key())).orElse(false);
    }

    @Override
    public boolean clear(String key) {
        return Optional.ofNullable(template().delete(key(key))).orElse(false);
    }

}
