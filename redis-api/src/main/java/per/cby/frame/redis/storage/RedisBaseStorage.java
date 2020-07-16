package per.cby.frame.redis.storage;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis存储接口
 * 
 * @author chenboyang
 *
 */
public interface RedisBaseStorage {

    /**
     * Redis存储操作模板
     * 
     * @return 操作模板
     */
    StringRedisTemplate template();

    /**
     * 键是否存在
     * 
     * @param key 键
     * @return 是否存在
     */
    boolean hasKey(String key);

}
