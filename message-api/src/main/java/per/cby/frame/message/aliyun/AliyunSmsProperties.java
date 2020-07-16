package per.cby.frame.message.aliyun;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 阿里云短信发送属性配置
 * 
 * @author chenboyang
 * @since 2019年5月17日
 *
 */
@Data
@Accessors(chain = true)
public class AliyunSmsProperties {

    /** 访问编号 */
    private String key;

    /** 访问密码 */
    private String secret;

    /** 签名 */
    private String sign;

}
