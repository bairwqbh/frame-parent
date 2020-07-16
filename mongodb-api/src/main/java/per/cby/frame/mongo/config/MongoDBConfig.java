package per.cby.frame.mongo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import per.cby.frame.mongo.handler.MongoDBHandler;
import per.cby.frame.mongo.storage.GridFsBucketManager;
import per.cby.frame.mongo.storage.GridFsStorage;

/**
 * MongoDB配置类
 * 
 * @author chenboyang
 *
 */
@Configuration("__MONGO_DB_CONFIG__")
public class MongoDBConfig {

    /**
     * MongoDB操作处理器
     * 
     * @param factory   连接工厂
     * @param converter 类型转换器
     * @return 操作处理器
     */
    @Bean
    @ConditionalOnMissingBean
    public MongoDBHandler mongoDBHandler(MongoDbFactory factory, MongoConverter converter) {
        return new MongoDBHandler(factory, converter);
    }

    /**
     * MongoDB存储板块管理服务
     * 
     * @return 板块管理
     */
    @Bean(GridFsBucketManager.BEAN_NAME)
    @ConditionalOnMissingBean
    public GridFsBucketManager gridFsBucketManager(MongoDBHandler mongoDBHandler) {
        return new GridFsBucketManager(mongoDBHandler);
    }

    /**
     * MongoDB文件存储服务
     * 
     * @return 文件存储服务
     */
    @Bean(GridFsStorage.BEAN_NAME)
    @ConditionalOnMissingBean
    public GridFsStorage gridFsStorage(MongoDBHandler mongoDBHandler) {
        return new GridFsStorage(mongoDBHandler);
    }

}
