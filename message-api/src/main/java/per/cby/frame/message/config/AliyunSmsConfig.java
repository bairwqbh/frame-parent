package per.cby.frame.message.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import per.cby.frame.message.VerifyDataCache;
import per.cby.frame.message.aliyun.AliyunMessageService;
import per.cby.frame.message.aliyun.AliyunSmsProperties;
import per.cby.frame.message.aliyun.AliyunVerifyService;

/**
 * 阿里云短信发送配置
 * 
 * @author chenboyang
 * @since 2019年5月17日
 *
 */
@Configuration("__ALIYUN_SMS_CONFIG__")
@ConditionalOnClass(name = "com.aliyuncs.IAcsClient")
@ConditionalOnProperty("sys.business.aliyun.sms.key")
public class AliyunSmsConfig {

    /**
     * 阿里云短信发送属性配置
     * 
     * @return 属性配置
     */
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("sys.business.aliyun.sms")
    public AliyunSmsProperties aliyunSmsProperties() {
        return new AliyunSmsProperties();
    }

    /**
     * 阿里云服务访问客户端
     * 
     * @return 客户端
     */
    @Bean
    @ConditionalOnMissingBean
    public IAcsClient iAcsClient(AliyunSmsProperties aliyunSmsProperties) {
        DefaultProfile profile = DefaultProfile.getProfile("default", aliyunSmsProperties.getKey(),
                aliyunSmsProperties.getSecret());
        return new DefaultAcsClient(profile);
    }

    /**
     * 阿里云短信服务
     * 
     * @return 短信服务
     */
    @Bean
    @ConditionalOnMissingBean
    public AliyunMessageService aliyunMessageService() {
        return new AliyunMessageService();
    }

    /**
     * 阿里云验证码发送服务
     * 
     * @return 验证码发送服务
     */
    @Bean
    @ConditionalOnMissingBean
    public AliyunVerifyService aliyunVerifyService(VerifyDataCache verifyDataCache) {
        return new AliyunVerifyService(verifyDataCache);
    }

}
