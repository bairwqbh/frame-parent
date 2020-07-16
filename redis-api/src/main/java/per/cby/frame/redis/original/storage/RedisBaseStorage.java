package per.cby.frame.redis.original.storage;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.redis.annotation.RedisStorage;
import per.cby.frame.redis.handler.RedisHandler;

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
public interface RedisBaseStorage<K, V> {

    /**
     * 获取操作处理器
     * 
     * @return 操作处理器
     */
    default RedisHandler<K, V> handler() {
        String handler = Optional.ofNullable(getClass().getAnnotation(RedisStorage.class)).map(RedisStorage::handler)
                .orElse(StringUtils.EMPTY);
        if (StringUtils.isNotBlank(handler)) {
            return SpringContextUtil.getBean(handler);
        }
        return SpringContextUtil.getBean(RedisHandler.class);
    }

}
