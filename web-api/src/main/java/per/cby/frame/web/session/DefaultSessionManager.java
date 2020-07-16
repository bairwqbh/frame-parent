package per.cby.frame.web.session;

import java.util.Objects;

/**
 * 默认会话管理器
 * 
 * @author chenboyang
 *
 */
public class DefaultSessionManager extends AbstractSessionManager {

    /** 当前会话 */
    private static final ThreadLocal<Session> CURRENT_SESSION = new ThreadLocal<Session>();

    @Override
    public Session getSession() {
        Session session = CURRENT_SESSION.get();
        if (session == null) {
            session = super.getSession();
            if (session != null) {
                CURRENT_SESSION.set(session);
            }
        }
        return session;
    }

    @Override
    public Session getSession(String id) {
        Session session = CURRENT_SESSION.get();
        if (session != null && Objects.equals(id, session.getId())) {
            return session;
        } else {
            return super.getSession(id);
        }
    }

    @Override
    public void stop(Session session) {
        super.stop(session);
        CURRENT_SESSION.remove();
    }

}
