package per.cby.frame.redis.constant;

/**
 * Redis业务常量
 * 
 * @author chenboyang
 * @since 2020年7月2日
 *
 */
public interface RedisConstant {

    /** 请求编号及密钥哈希存储实例名称 */
    String KEY_SECRET_HASH = "keySecretHash";

    /** 请求编号及密钥哈希存储默认名称 */
    String DEFAULT_KEY_SECRET_HASH = "unite:key:secret:hash";

}
