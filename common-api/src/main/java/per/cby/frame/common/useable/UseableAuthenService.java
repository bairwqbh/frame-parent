package per.cby.frame.common.useable;

/**
 * 用户认证服务接口
 * 
 * @author chenboyang
 *
 */
public interface UseableAuthenService {

    /**
     * 访问用户是否在线
     * 
     * @return 是否在线
     */
    boolean isAuthen();

    /**
     * 用户登录系统
     * 
     * @param account  账户名
     * @param password 密码
     * @return 访问令牌
     */
    String login(UseableService useableService, String account, String password);

    /**
     * 用户退出系统
     */
    void logout();

}
