package per.cby.frame.web.session;

import java.util.Optional;

import per.cby.frame.common.util.SpringContextUtil;

import lombok.experimental.UtilityClass;

/**
 * 会话管理帮助类
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class SessionManagerUtil {

    /** 会话管理器 */
    private SessionManager sessionManager = null;

    /**
     * 获取会话管理器
     * 
     * @return 会话管理器
     */
    public SessionManager sessionManager() {
        return Optional.ofNullable(sessionManager)
                .orElseGet(() -> sessionManager = SpringContextUtil.getBean(SessionManager.class));
    }

}
