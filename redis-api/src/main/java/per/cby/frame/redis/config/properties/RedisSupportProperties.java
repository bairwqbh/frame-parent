package per.cby.frame.redis.config.properties;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Redis配置属性辅助类
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RedisSupportProperties extends RedisProperties {

    /** 是否启用事务支持 */
    private boolean enableTransactionSupport = false;

}
