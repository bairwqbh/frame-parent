package per.cby.frame.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.cby.frame.message.VerifyDataCache;
import per.cby.frame.message.yunpian.YunpianMessageService;
import per.cby.frame.message.yunpian.YunpianVerifyService;

/**
 * 云片短信发送配置
 * 
 * @author chenboyang
 * @since 2019年5月17日
 *
 */
@Configuration("__YUNPIAN_CONFIG__")
@ConditionalOnProperty("sys.business.yunpian.key")
@ConditionalOnClass(name = "com.yunpian.sdk.YunpianClient")
public class YunpianConfig {

    @Value("${sys.business.yunpian.key}")
    private String key;

    /**
     * 阿里云短信服务
     * 
     * @return 短信服务
     */
    @Bean
    @ConditionalOnMissingBean
    public YunpianMessageService YunpianMessageService() {
        return new YunpianMessageService(key);
    }

    /**
     * 阿里云验证码发送服务
     * 
     * @return 验证码发送服务
     */
    @Bean
    @ConditionalOnMissingBean
    public YunpianVerifyService yunpianVerifyService(VerifyDataCache verifyDataCache) {
        return new YunpianVerifyService(verifyDataCache);
    }

}
