package per.cby.frame.web.session;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import per.cby.frame.common.model.time.TimeContainerFactory;
import per.cby.frame.common.model.time.TimeMap;
import per.cby.frame.common.util.IDUtil;
import per.cby.frame.common.util.SystemUtil;

/**
 * 简单会话缓存
 * 
 * @author chenboyang
 *
 */
public class SimpleSessionCache implements SessionCache {

    /** 默认容器名称前缀 */
    private static final String DEFAULT_PREFIX = "session_";

    /** 缓存容器名称 */
    private String name;

    /**
     * 构建简单会话缓存
     * 
     * @param name 缓存容器名称
     */
    public SimpleSessionCache() {
        this(DEFAULT_PREFIX + IDUtil.createTimeId());
    }

    /**
     * 构建简单会话缓存
     * 
     * @param name 缓存容器名称
     */
    public SimpleSessionCache(String name) {
        this.name = name;
    }

    @Override
    public boolean has(String key) {
        return sessionMap().containsKey(key);
    }

    @Override
    public Session get(String key) {
        return sessionMap().get(key);
    }

    @Override
    public void set(String key, Session value, long timeout, TimeUnit unit) {
        sessionMap().put(key, value, timeout, unit);
    }

    @Override
    public void remove(String key) {
        sessionMap().remove(key);
    }

    @Override
    public void clear() {
        sessionMap().clear();
    }

    @Override
    public Collection<String> getSessionIds() {
        return sessionMap().keySet();
    }

    @Override
    public Collection<Session> getSessions(Collection<String> sessionIds) {
        if (CollectionUtils.isEmpty(sessionIds)) {
            return null;
        }
        return sessionMap().entrySet().stream().filter(t -> sessionIds.contains(t.getKey())).map(Entry::getValue)
                .collect(Collectors.toList());
    }

    /**
     * 获取会话缓存
     * 
     * @return 缓存
     */
    private TimeMap<String, Session> sessionMap() {
        return SystemUtil.systemStorage().getOrSet(name,
                () -> TimeContainerFactory.createTimeHashMap(AbstractSessionManager.DEFAULT_TIMEOUT, 1000L,
                        AbstractSessionManager.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS));
    }

}
