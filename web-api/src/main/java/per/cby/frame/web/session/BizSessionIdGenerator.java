package per.cby.frame.web.session;

import java.util.Optional;

import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.common.util.IDUtil;
import per.cby.frame.web.constant.WebConstant;

/**
 * 业务会话编号生成器
 * 
 * @author chenboyang
 *
 */
public class BizSessionIdGenerator implements SessionIdGenerator, WebConstant {

    @Override
    public String generateId(Session session) {
        return BaseUtil.md5Encode(
                Optional.ofNullable(session.getBizId()).orElseGet(IDUtil::createUUID32) + SALT + session.getHost());
    }

}
