package per.cby.frame.message;

import java.util.Optional;

import per.cby.frame.redis.cache.RedisDataCache;
import per.cby.frame.redis.storage.value.CatalogRedisValueStorage;
import per.cby.frame.redis.storage.value.DefaultCatalogRedisValueStorage;

/**
 * Redis验证码存储容器
 * 
 * @author chenboyang
 *
 */
public class RedisVerifyCodeStorage extends RedisDataCache<String> implements VerifyDataCache {

    /** 短信验证码发送校验容器 */
    private final String MOBILE_MESSAGE_VERIFY = "mobile:message:verify";

    /** 验证码Redis集合组件 */
    private volatile CatalogRedisValueStorage<String> storage = null;

    @Override
    protected CatalogRedisValueStorage<String> storage() {
        return Optional.ofNullable(storage).orElseGet(
                () -> storage = new DefaultCatalogRedisValueStorage<String>(MOBILE_MESSAGE_VERIFY, String.class));
    }

}
