package per.cby.frame.oss.aliyun.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.cby.frame.oss.aliyun.AliyunOssBucketManager;
import per.cby.frame.oss.aliyun.AliyunOssService;
import per.cby.frame.oss.aliyun.AliyunOssServiceImpl;
import per.cby.frame.oss.aliyun.config.properties.AliyunOssProperties;

/**
 * 阿里云对象存储配置
 * 
 * @author chenboyang
 * @since 2019年10月25日
 *
 */
@Configuration("__ALIYUN_OSS_CONFIG__")
@ConditionalOnProperty("sys.business.oss.aliyun.accessId")
public class AliyunOssConfig {

    /**
     * 阿里云OSS对象存储配置属性
     * 
     * @return 配置属性
     */
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("sys.business.oss.aliyun")
    public AliyunOssProperties aliyunOssProperties() {
        return new AliyunOssProperties();
    }

    /**
     * 阿里云OSS对象存储板块管理服务
     * 
     * @return 板块管理
     */
    @Bean(AliyunOssBucketManager.BEAN_NAME)
    @ConditionalOnMissingBean
    public AliyunOssBucketManager aliyunOssBucketManager(AliyunOssService aliyunOssService) {
        return new AliyunOssBucketManager(aliyunOssService);
    }

    /**
     * 阿里云OSS存储服务
     * 
     * @return 文件存储服务
     */
    @Bean(AliyunOssService.BEAN_NAME)
    @ConditionalOnMissingBean
    public AliyunOssServiceImpl aliyunOssServiceImpl(AliyunOssProperties aliyunOssProperties) {
        return new AliyunOssServiceImpl(aliyunOssProperties);
    }

}
