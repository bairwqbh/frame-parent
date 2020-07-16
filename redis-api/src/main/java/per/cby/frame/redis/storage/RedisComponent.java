package per.cby.frame.redis.storage;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis组件抽象类
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public abstract class RedisComponent<K, V> {

    /**
     * 获取Redis存储服务
     * 
     * @return 存储服务
     */
    public abstract AbstractRedisBaseStorage<K, V> storage();

    /**
     * 设置Redis操作处理器
     * 
     * @param redisHandler Redis操作处理器
     */
    public void setRedisHandler(StringRedisTemplate template) {
        storage().setTemplate(template);
    }

}
