package per.cby.frame.message.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.cby.frame.message.RedisVerifyCodeStorage;
import per.cby.frame.message.VerifyDataCache;
import per.cby.frame.message.email.EmailService;
import per.cby.frame.message.email.impl.EmailServiceImpl;

/**
 * 消息发送应用配置
 * 
 * @author chenboyang
 * @since 2019年5月20日
 *
 */
@Configuration("__MESSAGE_CONFIG__")
public class MessageConfig {

    /**
     * 验证码数据缓存
     * 
     * @return 数据缓存
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = "per.cby.frame.redis.cache.RedisDataCache")
    public VerifyDataCache verifyDataCache() {
        return new RedisVerifyCodeStorage();
    }

    /**
     * 邮件发送服务
     * 
     * @return 邮件发送服务
     */
    @Bean
    @ConditionalOnMissingBean
    public EmailService emailService() {
        return new EmailServiceImpl();
    }

}
