package per.cby.frame.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.useable.Useable;
import per.cby.frame.common.useable.UseableAuthenService;
import per.cby.frame.common.useable.UseableService;
import per.cby.frame.common.util.JudgeUtil;
import per.cby.frame.web.constant.WebConstant;
import per.cby.frame.web.session.Session;
import per.cby.frame.web.session.SessionManager;

/**
 * 用户认证服务接口实现类
 * 
 * @author chenboyang
 *
 */
public class UseableAuthenServiceImpl implements UseableAuthenService, WebConstant {

    /** 会话管理器 */
    @Autowired(required = false)
    private SessionManager sessionManager;

    @Override
    public boolean isAuthen() {
        return sessionManager.hasSession();
    }

    @Override
    public String login(UseableService useableService, String account, String password) {
        Useable user = useableService.getAccount(account);
        BusinessAssert.notNull(user, "账号不存在！");
        String enPwd = useableService.passwordEncode(password);
        BusinessAssert.isTrue(JudgeUtil.isAllEqual(enPwd, user.password()), "密码错误！");
        BusinessAssert.isTrue(user.enable(), "账号已被禁用！");
        Session session = sessionManager.createSession(user.account());
        sessionManager.setAttribute(USER, user, session);
        return session.getId();
    }

    @Override
    public void logout() {
        sessionManager.stop();
    }

}
