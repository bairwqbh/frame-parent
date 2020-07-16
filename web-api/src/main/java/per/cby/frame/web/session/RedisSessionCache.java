package per.cby.frame.web.session;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;

import per.cby.frame.redis.storage.value.DefaultCatalogRedisValueStorage;

/**
 * 简单会话缓存
 * 
 * @author chenboyang
 *
 */
public class RedisSessionCache implements SessionCache {

    /** 默认容器名称前缀 */
    private static final String DEFAULT_NAME = "unite:session";

    /** Redis值存储容器 */
    private final DefaultCatalogRedisValueStorage<Session> storage;

    /**
     * 构建简单会话缓存
     * 
     * @param name 缓存容器名称
     */
    public RedisSessionCache() {
        this(DEFAULT_NAME);
    }

    /**
     * 构建简单会话缓存
     * 
     * @param name 缓存容器名称
     */
    public RedisSessionCache(String name) {
        storage = new DefaultCatalogRedisValueStorage<Session>(name, Session.class);
    }

    @Override
    public boolean has(String key) {
        return storage.has(key);
    }

    @Override
    public Session get(String key) {
        return storage.get(key);
    }

    @Override
    public void set(String key, Session value, long timeout, TimeUnit unit) {
        storage.set(key, value, timeout, unit);
    }

    @Override
    public void remove(String key) {
        storage.delete(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Collection<String> getSessionIds() {
        return storage.keys();
    }

    @Override
    public Collection<Session> getSessions(Collection<String> sessionIds) {
        if (CollectionUtils.isEmpty(sessionIds)) {
            return null;
        }
        return storage.multiGet(sessionIds);
    }

}
