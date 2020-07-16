package per.cby.frame.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.cby.frame.common.config.properties.SysProperties;
import per.cby.frame.common.db.sql.service.MyBatisService;
import per.cby.frame.common.db.sql.service.MyBatisServiceImpl;
import per.cby.frame.common.file.storage.bucket.SimpleBucketManager;
import per.cby.frame.common.file.storage.storage.SimpleFileStorage;
import per.cby.frame.common.file.storage.storage.SystemFileStorage;
import per.cby.frame.common.model.cache.SimpleDataCache;
import per.cby.frame.common.sys.service.SysService;
import per.cby.frame.common.sys.service.SysServiceImpl;
import per.cby.frame.common.sys.storage.DefaultSystemStorage;
import per.cby.frame.common.sys.storage.SystemStorage;
import per.cby.frame.common.tree.Treeable;
import per.cby.frame.common.tree.TreeableService;
import per.cby.frame.common.tree.TreeableServiceImpl;

/**
 * 基础配置类
 * 
 * @author chenboyang
 *
 */
@Configuration("__BASE_CONFIG__")
public class BaseConfig {

    /**
     * 系统业务属性配置
     * 
     * @return 属性配置
     */
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("sys")
    public SysProperties sysProperties() {
        return new SysProperties();
    }

    /**
     * 获取系统存储接口
     * 
     * @return 系统存储接口
     */
    @Bean
    @ConditionalOnMissingBean
    public SystemStorage systemStorage() {
        return new DefaultSystemStorage();
    }

    /**
     * 获取Mybatis数据访问通用服务
     * 
     * @return Mybatis数据访问通用服务
     */
    @Bean
    @ConditionalOnMissingBean
    public MyBatisService myBatisService() {
        return new MyBatisServiceImpl();
    }

    /**
     * 获取系统服务接口
     * 
     * @return 系统服务接口
     */
    @Bean
    @ConditionalOnMissingBean
    public SysService sysService() {
        return new SysServiceImpl();
    }

    /**
     * 获取树状数据服务接口
     * 
     * @return 树状数据服务接口
     */
    @Bean
    @ConditionalOnMissingBean
    public <T extends Treeable<T>> TreeableService<T> treeableService() {
        return new TreeableServiceImpl<T>();
    }

    /**
     * 获取简单数据缓存
     * 
     * @return 简单数据缓存
     */
    @Bean
    @ConditionalOnMissingBean
    public <K, V> SimpleDataCache<K, V> simpleDataCache() {
        return new SimpleDataCache<K, V>();
    }

    /**
     * 简单存储板块管理服务
     * 
     * @return 板块管理
     */
    @Bean(SimpleBucketManager.BEAN_NAME)
    @ConditionalOnMissingBean
    public SimpleBucketManager simpleBucketManager() {
        return new SimpleBucketManager();
    }

    /**
     * 简单文件存储服务
     * 
     * @return 文件存储服务
     */
    @Bean(SystemFileStorage.BEAN_NAME)
    @ConditionalOnMissingBean
    public SimpleFileStorage simpleFileStorage() {
        return new SimpleFileStorage();
    }

}
