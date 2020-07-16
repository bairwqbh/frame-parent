package per.cby.frame.web.session;

import java.util.Map;

import per.cby.frame.common.model.time.TimeContainerFactory;
import per.cby.frame.common.util.SystemUtil;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 二级缓存会话管理器
 * 
 * @author chenboyang
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class L2CacheSessionManager extends AbstractSessionManager {

    /** 默认二级缓存容器名称 */
    private final String DEFAULT_NAME = "session_l2Cache";

    /** 默认二级缓存过期时间 */
    private final long DEFAULT_TIMEOUT = 1000L * 5;

    /** 二级缓存容器名称 */
    private String name = DEFAULT_NAME;

    /** 二级缓存过期时间 */
    private long timeout = DEFAULT_TIMEOUT;

    /**
     * 构建二级缓存会话管理器
     * 
     * @param name 容器名称
     */
    public L2CacheSessionManager(String name) {
        this.name = name;
    }

    /**
     * 构建二级缓存会话管理器
     * 
     * @param timeout 过期时间
     */
    public L2CacheSessionManager(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public Session getSession(String id) {
        Session session = l2Cache().get(id);
        if (session == null) {
            session = super.getSession(id);
            if (session != null) {
                l2Cache().put(session.getId(), session);
            }
        }
        return session;
    }

    @Override
    public void stop(Session session) {
        super.stop(session);
        l2Cache().remove(session.getId());
    }

    /**
     * 会话二级缓存
     * 
     * @return 缓存
     */
    private Map<String, Session> l2Cache() {
        return SystemUtil.systemStorage().getOrSet(name, () -> TimeContainerFactory.createTimeHashMap(timeout));
    }

}
