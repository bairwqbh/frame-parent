package per.cby.frame.redis.cache;

import java.util.concurrent.TimeUnit;

import per.cby.frame.common.model.cache.DataCache;
import per.cby.frame.redis.storage.value.CatalogRedisValueStorage;

/**
 * Redis数据缓存接口抽象类
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
public abstract class RedisDataCache<T> implements DataCache<String, T> {

    @Override
    public boolean has(String key) {
        return storage().has(key);
    }

    @Override
    public T get(String key) {
        return storage().get(key);
    }

    @Override
    public void set(String key, T value, long timeout, TimeUnit unit) {
        storage().set(key, value, timeout, unit);
    }

    @Override
    public void remove(String key) {
        storage().delete(key);
    }

    @Override
    public void clear() {
        storage().clear();
    }

    /**
     * 获取Redis缓存存储
     * 
     * @return Redis缓存存储
     */
    protected abstract CatalogRedisValueStorage<T> storage();

}
