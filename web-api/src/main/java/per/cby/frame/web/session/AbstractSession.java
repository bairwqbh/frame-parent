package per.cby.frame.web.session;

import java.time.LocalDateTime;

import per.cby.frame.common.exception.BusinessAssert;

/**
 * 抽象会话
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractSession implements Session {

    /**
     * 设置会话编号
     * 
     * @param id 会话编号
     */
    public abstract void setId(String id);

    /**
     * 设置会话最后访问时间
     * 
     * @param time 时间
     */
    public abstract void setLastAccessTime(LocalDateTime time);

    /**
     * 设置会话结束时间
     * 
     * @param stopTime 结束时间
     */
    public abstract void setStopTime(LocalDateTime stopTime);

    /**
     * 设置会话过期时间
     * 
     * @param timeout 过期时间
     */
    public abstract void setTimeout(long timeout);

    /**
     * 设置会话属性
     * 
     * @param key   属性名称
     * @param value 属性值
     */
    public abstract void setAttribute(String key, Object value);

    /**
     * 删除会话属性
     * 
     * @param key 属性名称
     * @return 属性值
     */
    public abstract <T> T removeAttribute(String key);

    @Override
    public boolean isTimeout() {
        return getStopTime().isBefore(LocalDateTime.now());
    }

    @Override
    public void validate() {
        BusinessAssert.isTrue(!isTimeout(), "会话已过期！");
    }

}
