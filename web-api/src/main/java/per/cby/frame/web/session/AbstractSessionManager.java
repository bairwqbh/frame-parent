package per.cby.frame.web.session;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.util.DateUtil;
import per.cby.frame.web.service.ApplyService;

import lombok.Getter;
import lombok.Setter;

/**
 * 默认会话管理器
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractSessionManager implements SessionManager {

    /** 默认过期时间 */
    protected static final long DEFAULT_TIMEOUT = 1000L * 60 * 30;

    /** 全局过期时间 */
    @Getter
    private long globalTimeout = DEFAULT_TIMEOUT;

    /** 会话缓存 */
    @Setter
    @Getter
    private SessionCache sessionCache = new SimpleSessionCache();

    /** 应用服务 */
    @Getter
    @Autowired(required = false)
    private ApplyService applyService;

    /** 会话编号生成器 */
    @Setter
    @Getter
    private SessionIdGenerator sessionIdGenerator = new UuidSessionIdGenerator();

    /** 会话工厂 */
    @Setter
    @Getter
    private SessionFactory sessionFactory = new SimpleSessionFactory();

    @Override
    public boolean hasSession() {
        return hasSession(applyService.currToken());
    }

    @Override
    public boolean hasSession(String id) {
        return sessionCache.has(id);
    }

    @Override
    public void setGlobalTimeout(long timeout, TimeUnit timeUnit) {
        this.globalTimeout = timeUnit == TimeUnit.MILLISECONDS ? timeout
                : TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
    }

    @Override
    public Session createSession(String bizId, String ip) {
        ip = Optional.ofNullable(ip)
                .orElseGet(() -> Optional.ofNullable(applyService).map(t -> t.request().getRemoteAddr()).orElse(""));
        Session session = sessionFactory.create(bizId, ip, globalTimeout);
        if (session instanceof AbstractSession) {
            String id = sessionIdGenerator.generateId(session);
            AbstractSession as = (AbstractSession) session;
            as.setId(id);
        }
        setTimeout(session.getTimeout(), session);
        return session;
    }

    @Override
    public Session getSession() {
        return getSession(applyService.currToken());
    }

    @Override
    public Session getSession(String id) {
        return sessionCache.get(id);
    }

    @Override
    public Collection<String> getSessionIds() {
        return sessionCache.getSessionIds();
    }

    @Override
    public Collection<Session> getSessions(Collection<String> sessionIds) {
        return sessionCache.getSessions(sessionIds);
    }

    @Override
    public void setTimeout(long timeout, TimeUnit timeUnit, Session session) {
        if (session instanceof AbstractSession) {
            AbstractSession abstractSession = (AbstractSession) session;
            long milliSeconds = timeUnit == TimeUnit.MILLISECONDS ? timeout
                    : TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
            if (milliSeconds < 1) {
                milliSeconds = globalTimeout;
            }
            abstractSession.setTimeout(milliSeconds);
            LocalDateTime stopTime = LocalDateTime.now().plus(milliSeconds, ChronoUnit.MILLIS);
            abstractSession.setStopTime(stopTime);
            sessionCache.set(abstractSession.getId(), abstractSession, timeout, timeUnit);
        }
    }

    @Override
    public void touch(Session session) {
        if (session instanceof AbstractSession) {
            AbstractSession abstractSession = (AbstractSession) session;
            abstractSession.setLastAccessTime(LocalDateTime.now());
            setTimeout(abstractSession.getTimeout(), abstractSession);
        }
    }

    @Override
    public void stop(Session session) {
        Optional.ofNullable(session).map(Session::getId).ifPresent(sessionCache::remove);
    }

    @Override
    public void setAttribute(String key, Object value, Session session) {
        if (session instanceof AbstractSession) {
            AbstractSession abstractSession = (AbstractSession) session;
            abstractSession.setAttribute(key, value);
            setSession(abstractSession);
        }
    }

    @Override
    public <T> T removeAttribute(String key, Session session) {
        T t = null;
        if (session instanceof AbstractSession) {
            AbstractSession abstractSession = (AbstractSession) session;
            t = abstractSession.removeAttribute(key);
            setSession(abstractSession);
        }
        return t;
    }

    /**
     * 设置会话信息
     * 
     * @param session 会话信息
     */
    protected void setSession(Session session) {
        session.validate();
        long timeout = DateUtil.atDate(session.getStopTime()).getTime() - System.currentTimeMillis();
        sessionCache.set(session.getId(), session, timeout, TimeUnit.MILLISECONDS);
    }

}
