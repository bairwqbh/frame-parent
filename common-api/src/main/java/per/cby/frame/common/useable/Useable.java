package per.cby.frame.common.useable;

/**
 * 统一用户接口
 * 
 * @author chenboyang
 *
 */
public interface Useable {

    /**
     * 获取帐号
     * 
     * @return 帐号
     */
    String account();

    /**
     * 获取密码
     * 
     * @return 密码
     */
    String password();

    /**
     * 判断用户是否启用
     * 
     * @return 是否启用
     */
    boolean enable();

}
