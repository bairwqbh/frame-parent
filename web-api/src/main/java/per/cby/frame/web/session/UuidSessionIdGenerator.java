package per.cby.frame.web.session;

import per.cby.frame.common.util.IDUtil;

/**
 * UUID会话编号生成器
 * 
 * @author chenboyang
 *
 */
public class UuidSessionIdGenerator implements SessionIdGenerator {

    @Override
    public String generateId(Session session) {
        return IDUtil.createUUID32();
    }

}
