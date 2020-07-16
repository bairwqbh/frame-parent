package per.cby.frame.web.session;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * 会话信息
 * 
 * @author chenboyang
 *
 */
@JsonTypeInfo(use = Id.CLASS)
@JsonSubTypes(@Type(SimpleSession.class))
public interface Session {

    /**
     * 获取会话编号
     * 
     * @return 会话编号
     */
    String getId();

    /**
     * 获取业务编号
     * 
     * @return 业务编号
     */
    String getBizId();

    /**
     * 获取会话开始时间
     * 
     * @return 开始时间
     */
    LocalDateTime getStartTime();

    /**
     * 获取会话最后访问时间
     * 
     * @return 最后访问时间
     */
    LocalDateTime getLastAccessTime();

    /**
     * 获取会话停止时间
     * 
     * @return 停止时间
     */
    LocalDateTime getStopTime();

    /**
     * 获取会话超时时长
     * 
     * @return 超时时长
     */
    long getTimeout();

    /**
     * 获取会话访问地址
     * 
     * @return 访问地址
     */
    String getHost();

    /**
     * 获取会话存储的属性名称列表
     * 
     * @return 属性名称列表
     */
    Collection<String> getAttributeKeys();

    /**
     * 获取会话属性信息
     * 
     * @param key 属性名称
     * @return 属性值
     */
    <T> T getAttribute(String key);

    /**
     * 获取会话属性信息
     * 
     * @return 属性信息
     */
    Map<String, Object> attribute();

    /**
     * 会话是否过期
     * 
     * @return 是否过期
     */
    boolean isTimeout();

    /**
     * 校验会话
     */
    void validate();

}
