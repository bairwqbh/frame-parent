package per.cby.frame.web.session;

import java.util.Collection;

import per.cby.frame.common.model.cache.DataCache;

/**
 * 会话缓存
 * 
 * @author chenboyang
 *
 */
public interface SessionCache extends DataCache<String, Session> {

    /**
     * 获取会话编号集合
     * 
     * @return 编号集合
     */
    Collection<String> getSessionIds();

    /**
     * 根据会话编号集合获取会话集合
     * 
     * @param sessionIds 编号集合
     * @return 会话集合
     */
    Collection<Session> getSessions(Collection<String> sessionIds);

}
