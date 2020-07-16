package per.cby.frame.redis.storage;

import org.apache.commons.lang3.StringUtils;

/**
 * Redis容器存储接口
 * 
 * @author chenboyang
 */
public interface RedisContainerStorage extends RedisBaseStorage {

    /**
     * 获取容器键
     * 
     * @return 容器键
     */
    String key();

    /**
     * 组装存储键
     * 
     * @param key 业务键
     * @return 存储键
     */
    default String key(String key) {
        if (StringUtils.isBlank(key())) {
            return key;
        }
        if (StringUtils.isBlank(key)) {
            return key();
        }
        return key() + ":" + key;
    }

    /**
     * 键是否存在
     * 
     * @param key 键
     * @return 是否存在
     */
    default boolean existKey(String key) {
        return hasKey(key(key));
    }

    /**
     * 清除整个容器
     * 
     * @return 是否清除完成
     */
    boolean clear();

    /**
     * 根据键清除容器元素
     * 
     * @param key 业务键
     * @return 是否清除完成
     */
    boolean clear(String key);

}
