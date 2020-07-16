package per.cby.frame.redis.storage.value;

import java.util.Collection;

import per.cby.frame.redis.storage.RedisContainerStorage;

/**
 * Redis目录结构值存储接口
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
public interface CatalogRedisValueStorage<T> extends RedisValueStorage<String, T>, RedisContainerStorage {

    /**
     * 获取键集合
     * 
     * @return 键集合
     */
    Collection<String> keys();

}
