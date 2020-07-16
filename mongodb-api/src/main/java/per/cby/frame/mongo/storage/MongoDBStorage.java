package per.cby.frame.mongo.storage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import per.cby.frame.common.model.Page;
import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.mongo.annotation.MongoStorage;
import per.cby.frame.mongo.handler.MongoDBHandler;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * MongoDB存储接口
 * 
 * @author chenboyang
 *
 * @param <T> 业务对象类型
 */
@SuppressWarnings("unchecked")
public interface MongoDBStorage<T> {

    /**
     * 配置项
     * 
     * @return 配置项
     */
    default MongoStorage mongoConf() {
        return getClass().getAnnotation(MongoStorage.class);
    }

    /**
     * 获取存储集合名称
     * 
     * @return 存储集合名称
     */
    default String collectionName() {
        return Optional.ofNullable(mongoConf()).map(MongoStorage::name).orElse(StringUtils.EMPTY);
    }

    /**
     * 实体对象类型
     * 
     * @return 类型
     */
    default Class<T> entityType() {
        Class<?> clazz = Object.class;
        if (mongoConf() != null && !void.class.equals(mongoConf().type())) {
            clazz = mongoConf().type();
        }
        return (Class<T>) clazz;
    }

    /**
     * Mongo操作对象
     * 
     * @return 操作对象
     */
    default MongoDBHandler handler() {
        String name = Optional.ofNullable(mongoConf()).map(MongoStorage::handler).orElse(StringUtils.EMPTY);
        if (StringUtils.isNotBlank(name)) {
            return SpringContextUtil.getBean(name);
        }
        return SpringContextUtil.getBean(MongoDBHandler.class);
    }

    /**
     * 删除存储集合
     */
    default void drop() {
        handler().dropCollection(collectionName());
    }

    /**
     * 查询对象是否存在
     * 
     * @param query 查询条件
     * @return 是否存在
     */
    default boolean exists(Query query) {
        return handler().exists(query, collectionName());
    }

    /**
     * 列表查询
     * 
     * @param query 查询条件
     * @return 对象列表
     */
    default List<T> find(Query query) {
        return handler().find(query, entityType(), collectionName());
    }

    /**
     * 查询存储集合全部数据
     * 
     * @return 对象列表
     */
    default List<T> findAll() {
        return handler().findAll(entityType(), collectionName());
    }

    /**
     * 查询单个对象
     * 
     * @param query 查询条件
     * @return 对象
     */
    default T findOne(Query query) {
        return handler().findOne(query, entityType(), collectionName());
    }

    /**
     * 根据ID查询对象
     * 
     * @param id 对象ID
     * @return 对象
     */
    default T findById(Object id) {
        return handler().findById(id, entityType(), collectionName());
    }

    /**
     * 查询记录数
     * 
     * @param query 查询条件
     * @return 记录数
     */
    default long count(Query query) {
        return handler().count(query, collectionName());
    }

    /**
     * 查询全部记录数
     * 
     * @return 记录数
     */
    default long count() {
        return handler().count(null, collectionName());
    }

    /**
     * 保存实体对象
     * 
     * @param entity 实体对象
     */
    default void save(T entity) {
        handler().save(entity, collectionName());
    }

    /**
     * 插入对象集合
     * 
     * @param colletion 对象集合
     */
    default void insertAll(Collection<? extends T> colletion) {
        handler().insert(colletion, collectionName());
    }

    /**
     * 根据查询条件更新数据
     * 
     * @param query  查询条件
     * @param update 更新信息
     * @return 写入结果
     */
    default UpdateResult update(Query query, Update update) {
        return handler().updateMulti(query, update, collectionName());
    }

    /**
     * 根据ID删除对象
     * 
     * @param id 实体主键
     * @return 写入结果
     */
    default DeleteResult removeById(Object id) {
        return handler().remove(Query.query(Criteria.where("_id").is(id)), entityType());
    }

    /**
     * 删除对象
     * 
     * @param entity 实体对象
     * @return 写入结果
     */
    default DeleteResult remove(T entity) {
        return handler().remove(entity, collectionName());
    }

    /**
     * 根据条件删除对象
     * 
     * @param query 查询条件
     * @return 写入结果
     */
    default DeleteResult remove(Query query) {
        return handler().remove(query, collectionName());
    }

    /**
     * 基础分页转MongoDB分页
     * 
     * @param page 基础分页
     * @return MongoDB分页
     */
    default PageRequest pageToPr(Page<T> page) {
        return PageRequest.of(page.getPage() - 1, page.getSize());
    }

    /**
     * 分页查询
     * 
     * @param page  分页信息
     * @param query 查询
     * @return 分页信息
     */
    default Page<T> page(Page<T> page) {
        return page(page, new Query());
    }

    /**
     * 分页查询
     * 
     * @param page  分页信息
     * @param query 查询
     * @return 分页信息
     */
    default Page<T> page(Page<T> page, Query query) {
        page.setTotal(Long.valueOf(count(query)).intValue());
        page.setList(find(query.with(pageToPr(page))));
        return page;
    }

}
