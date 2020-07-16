package per.cby.frame.redis.original.storage.value;

import per.cby.frame.redis.handler.RedisHandler;
import per.cby.frame.redis.original.storage.RedisComponent;

import lombok.NoArgsConstructor;

/**
 * Redis值存储接口简单实现类
 * 
 * @author chenboyang
 *
 * @param <V> 值类型
 */
@Deprecated
@NoArgsConstructor
public class SimpleRedisValueStorage<V> extends RedisComponent<String, V> implements CatalogRedisValueStorage<V> {

    /**
     * 构建Redis值存储
     * 
     * @param name 容器名称
     */
    public SimpleRedisValueStorage(String name) {
        this.name = name;
    }

    /**
     * 构建Redis值存储
     * 
     * @param name 容器名称
     */
    public SimpleRedisValueStorage(String name, RedisHandler<String, V> handler) {
        this.name = name;
        this.redisHandler = handler;
    }

    @Override
    public String keyPrefix() {
        String prefix = componentName();
        if (prefix.endsWith(SEPARATOR)) {
            return prefix;
        }
        return prefix + SEPARATOR;
    }

}
