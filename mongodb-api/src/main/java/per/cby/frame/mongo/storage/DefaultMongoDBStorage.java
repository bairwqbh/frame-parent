package per.cby.frame.mongo.storage;

import per.cby.frame.mongo.handler.MongoDBHandler;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 默认MongoDB存储
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@RequiredArgsConstructor
public class DefaultMongoDBStorage<T> implements MongoDBStorage<T> {

    /** 集合名称 */
    private final String collectionName;

    /** 实体对象类型 */
    private final Class<T> entityType;

    /** MongoDB处理器 */
    @Setter
    private MongoDBHandler handler;

    @Override
    public String collectionName() {
        return collectionName;
    }

    @Override
    public Class<T> entityType() {
        return entityType;
    }

    @Override
    public MongoDBHandler handler() {
        if (handler == null) {
            handler = MongoDBStorage.super.handler();
        }
        return handler;
    }

}
