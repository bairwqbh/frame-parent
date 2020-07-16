package per.cby.frame.oss.qiniu.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.cby.frame.oss.qiniu.QiniuOssBucketManager;
import per.cby.frame.oss.qiniu.QiniuOssBucketService;
import per.cby.frame.oss.qiniu.QiniuOssService;
import per.cby.frame.oss.qiniu.QiniuOssServiceImpl;
import per.cby.frame.oss.qiniu.config.properties.QiniuOssProperties;

/**
 * 阿里云对象存储配置
 * 
 * @author chenboyang
 * @since 2019年10月25日
 *
 */
@Configuration("__QINIU_OSS_CONFIG__")
@ConditionalOnProperty("sys.business.oss.qiniu.accessKey")
public class QiniuOssConfig {

    /**
     * 七牛云OSS对象存储配置属性
     * 
     * @return 配置属性
     */
    @Bean
    @ConfigurationProperties("sys.business.oss.qiniu")
    public QiniuOssProperties qiniuOssProperties() {
        return new QiniuOssProperties();
    }

    /**
     * 七牛云OSS对象存储板块管理器
     * 
     * @return 板块管理
     */
    @Bean(QiniuOssBucketService.BEAN_NAME)
    public QiniuOssBucketManager qiniuOssBucketManager(QiniuOssProperties qiniuOssProperties) {
        return new QiniuOssBucketManager(qiniuOssProperties);
    }

    /**
     * 七牛云OSS存储服务
     * 
     * @return 文件存储服务
     */
    @Bean(QiniuOssService.BEAN_NAME)
    public QiniuOssServiceImpl qiniuOssServiceImpl(QiniuOssProperties qiniuOssProperties) {
        return new QiniuOssServiceImpl(qiniuOssProperties);
    }

}
