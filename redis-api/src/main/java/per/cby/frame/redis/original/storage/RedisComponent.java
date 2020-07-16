package per.cby.frame.redis.original.storage;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import per.cby.frame.redis.handler.RedisHandler;

/**
 * Redis组件抽象类
 * 
 * @author chenboyang
 *
 */
@Deprecated
public abstract class RedisComponent<K, V> implements RedisBaseStorage<K, V> {

    /** 容器名称 */
    protected String name;

    /** Redis操作处理器 */
    protected RedisHandler<K, V> redisHandler;

    /**
     * 获取容器键
     * 
     * @return 容器键
     */
    public String componentName() {
        if (StringUtils.isNotBlank(name)) {
            return name;
        }
        return getClass().getName();
    }

    /**
     * 获取Redis操作处理器
     * 
     * @return Redis操作处理器
     */
    public RedisHandler<K, V> getRedisHandler() {
        return Optional.ofNullable(redisHandler).orElseGet(RedisBaseStorage.super::handler);
    }

    /**
     * 设置Redis操作处理器
     * 
     * @param redisHandler Redis操作处理器
     */
    public void setRedisHandler(RedisHandler<K, V> redisHandler) {
        this.redisHandler = redisHandler;
    }

    @Override
    public RedisHandler<K, V> handler() {
        return getRedisHandler();
    }

}
