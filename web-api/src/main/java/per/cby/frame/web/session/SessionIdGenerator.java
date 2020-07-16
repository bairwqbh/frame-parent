package per.cby.frame.web.session;

/**
 * 会话编号生成器
 * 
 * @author chenboyang
 *
 */
public interface SessionIdGenerator {

    /**
     * 生成编号
     * 
     * @param session 会话信息
     * @return 编号
     */
    String generateId(Session session);

}
