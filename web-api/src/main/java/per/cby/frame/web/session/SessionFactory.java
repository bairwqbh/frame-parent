package per.cby.frame.web.session;

/**
 * 会话工厂
 * 
 * @author chenboyang
 */
public interface SessionFactory {

    /**
     * 创建会话
     * 
     * @param bizId   业务编号
     * @param host    请求地址
     * @param timeout 过期时间
     * @return 会话信息
     */
    Session create(String bizId, String host, long timeout);

}
