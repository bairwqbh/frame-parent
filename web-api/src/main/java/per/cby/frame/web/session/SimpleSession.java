package per.cby.frame.web.session;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 简单会话信息
 * 
 * @author chenboyang
 *
 */
@Data
@SuppressWarnings("unchecked")
@EqualsAndHashCode(callSuper = false)
public class SimpleSession extends AbstractSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 会话编号 */
    private String id;

    /** 业务编号 */
    private String bizId;

    /** 会话开始时间 */
    private LocalDateTime startTime;

    /** 最后访问时间 */
    private LocalDateTime lastAccessTime;

    /** 会话结束时间 */
    private LocalDateTime stopTime;

    /** 会话过期时长 */
    private long timeout;

    /** 访问地址 */
    private String host;

    /** 会话属性 */
    private Map<String, Object> attributes;

    /**
     * 构建简单会话
     */
    public SimpleSession() {
        startTime = LocalDateTime.now();
        lastAccessTime = LocalDateTime.now();
        attributes = new HashMap<String, Object>();
    }

    @Override
    public Collection<String> getAttributeKeys() {
        return attributes.keySet();
    }

    @Override
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public <T> T removeAttribute(String key) {
        return (T) attributes.remove(key);
    }

    @Override
    public Map<String, Object> attribute() {
        return attributes;
    }

}
