package per.cby.frame.mongo.factory;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

/**
 * MongoDB数据库操作模板构建工厂接口
 * 
 * @author chenboyang
 *
 */
public interface MongoTemplateFactory {

    /**
     * 构建MongoDB处理模板
     * 
     * @return 处理模板
     */
    MongoTemplate buildTemplate();

    /**
     * 构建MongoDB处理模板
     * 
     * @param factory   MongoDb工厂
     * @param converter MongoDb转换器
     * @return 处理模板
     */
    MongoTemplate buildTemplate(MongoDbFactory factory, MongoConverter converter);

}
