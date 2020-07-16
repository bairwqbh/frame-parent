package per.cby.frame.web.service;

/**
 * 验证签名服务
 * 
 * @author chenboyang
 * @since 2020年4月30日
 *
 */
public interface ValidateSignService {

    /**
     * 根据关键标识获取密钥
     * 
     * @param key 关键标识
     * @return 密钥
     */
    String getSecret(String key);

    /**
     * 生成签名
     * 
     * @param key       关键标识
     * @param secret    密钥
     * @param timestamp 时间戳
     * @return 签名
     */
    String genSign(String key, String secret, String timestamp);

}
