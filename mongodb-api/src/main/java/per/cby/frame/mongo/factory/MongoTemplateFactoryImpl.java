package per.cby.frame.mongo.factory;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import per.cby.frame.common.util.SpringContextUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * MongoDB数据库操作模板构建工厂接口实现类
 * 
 * @author chenboyang
 *
 */
@Data
@AllArgsConstructor
public class MongoTemplateFactoryImpl implements MongoTemplateFactory {

    /** MongoDb工厂 */
    private MongoDbFactory factory;

    /** MongoDb转换器 */
    private MongoConverter converter;

    public MongoTemplateFactoryImpl() {
        this(SpringContextUtil.getBean(MongoDbFactory.class), SpringContextUtil.getBean(MongoConverter.class));
    }

    @Override
    public MongoTemplate buildTemplate() {
        return new MongoTemplate(factory, converter);
    }

    @Override
    public MongoTemplate buildTemplate(MongoDbFactory factory, MongoConverter converter) {
        return new MongoTemplate(factory, converter);
    }

}
