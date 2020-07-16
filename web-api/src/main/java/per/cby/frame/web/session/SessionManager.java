package per.cby.frame.web.session;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 会话管理器
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings("unchecked")
public interface SessionManager {

    /**
     * 当前会话是否存在
     * 
     * @return 是否存在
     */
    boolean hasSession();

    /**
     * 会话是否存在
     * 
     * @param id 会话编号
     * @return 是否存在
     */
    boolean hasSession(String id);

    /**
     * 设置全局过期时间
     * 
     * @param timeout 过期时间
     */
    default void setGlobalTimeout(long timeout) {
        setGlobalTimeout(timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置全局过期时间
     * 
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    void setGlobalTimeout(long timeout, TimeUnit timeUnit);

    /**
     * 创建会话
     * 
     * @return 会话信息
     */
    default Session createSession() {
        return createSession(null);
    }

    /**
     * 创建会话
     * 
     * @param bizId 业务唯一编号
     * @return 会话信息
     */
    default Session createSession(String bizId) {
        return createSession(bizId, null);
    }

    /**
     * 创建会话
     * 
     * @param bizId 业务唯一编号
     * @param host  请求地址
     * @return 会话信息
     */
    Session createSession(String bizId, String host);

    /**
     * 获取当前会话
     * 
     * @return 会话
     */
    Session getSession();

    /**
     * 根据会话编号获取会话
     * 
     * @param id 会话编号
     * @return 会话
     */
    Session getSession(String id);

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

    /**
     * 设置当前会话超时时长
     * 
     * @param timeout 超时时长
     */
    default void setTimeout(long timeout) {
        setTimeout(timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置当前会话超时时长
     * 
     * @param timeout  超时时长
     * @param timeUnit 时间单位
     */
    default void setTimeout(long timeout, TimeUnit timeUnit) {
        setTimeout(timeout, timeUnit, getSession());
    }

    /**
     * 设置会话超时时长
     * 
     * @param timeout 超时时长
     * @param id      会话编号
     */
    default void setTimeout(long timeout, String id) {
        setTimeout(timeout, getSession(id));
    }

    /**
     * 设置会话超时时长
     * 
     * @param timeout 超时时长
     * @param session 会话信息
     */
    default void setTimeout(long timeout, Session session) {
        setTimeout(timeout, TimeUnit.MILLISECONDS, session);
    }

    /**
     * 设置会话超时时长
     * 
     * @param timeout  超时时长
     * @param timeUnit 时间单位
     * @param id       会话编号
     */
    default void setTimeout(long timeout, TimeUnit timeUnit, String id) {
        setTimeout(timeout, TimeUnit.MILLISECONDS, getSession(id));
    }

    /**
     * 设置会话超时时长
     * 
     * @param timeout  超时时长
     * @param timeUnit 时间单位
     * @param session  会话信息
     */
    void setTimeout(long timeout, TimeUnit timeUnit, Session session);

    /**
     * 接触当前会话
     */
    default void touch() {
        touch(getSession());
    }

    /**
     * 接触当前会话
     * 
     * @param id 会话编号
     */
    default void touch(String id) {
        touch(getSession(id));
    }

    /**
     * 接触会话
     * 
     * @param session 会话信息
     */
    void touch(Session session);

    /**
     * 停止当前会话
     */
    default void stop() {
        stop(getSession());
    }

    /**
     * 停止当前会话
     * 
     * @param id 会话编号
     */
    default void stop(String id) {
        stop(getSession(id));
    }

    /**
     * 停止会话
     * 
     * @param session 会话信息
     */
    void stop(Session session);

    /**
     * 设置当前会话属性信息
     * 
     * @param key   属性名称
     * @param value 属性值
     */
    default void setAttribute(String key, Object value) {
        setAttribute(key, value, getSession());
    }

    /**
     * 设置当前会话属性信息
     * 
     * @param key   属性名称
     * @param value 属性值
     * @param id    会话编号
     */
    default void setAttribute(String key, Object value, String id) {
        setAttribute(key, value, getSession(id));
    }

    /**
     * 设置会话属性信息
     * 
     * @param key     属性名称
     * @param value   属性值
     * @param session 会话信息
     */
    void setAttribute(String key, Object value, Session session);

    /**
     * 删除当前会话属性
     * 
     * @param key 属性名称
     * @return 属性值
     */
    default <T> T removeAttribute(String key) {
        return (T) removeAttribute(key, getSession());
    }

    /**
     * 删除当前会话属性
     * 
     * @param key 属性名称
     * @param id  会话编号
     * @return 属性值
     */
    default <T> T removeAttribute(String key, String id) {
        return (T) removeAttribute(key, getSession(id));
    }

    /**
     * 删除会话属性
     * 
     * @param key     属性名称
     * @param session 会话信息
     * @return 属性值
     */
    <T> T removeAttribute(String key, Session session);

}
