package per.cby.frame.redis.original.storage;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import per.cby.frame.redis.annotation.RedisStorage;

/**
 * Redis容器存储接口
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public interface RedisContainerStorage<K, V> extends RedisBaseStorage<K, V> {

    /**
     * 获取容器键
     * 
     * @return 容器键
     */
    default K key() {
        return (K) Optional.ofNullable(getClass().getAnnotation(RedisStorage.class)).map(RedisStorage::value)
                .orElse(StringUtils.EMPTY);
    }

    /**
     * 清除整个容器
     */
    default void clear() {
        handler().delete(key());
    }

}
