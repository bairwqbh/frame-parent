package per.cby.frame.redis.strategy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import per.cby.frame.common.util.ReflectUtil;
import per.cby.frame.mongo.storage.MongoDBStorage;
import per.cby.frame.redis.storage.hash.DefaultRedisHashStorage;

/**
 * Redis和MongoDB的策略服务
 * 
 * @author chenboyang
 * @since 2019年12月13日
 *
 * @param <T> 业务类型
 */
public abstract class RedisMongoStrategy<T> extends DefaultRedisHashStorage<String, T> implements MongoDBStorage<T> {

    @Override
    public boolean has(String key) {
        boolean has = super.has(key);
        return reload(has, has, key, t -> t != null);
    }

    @Override
    public T get(String key) {
        T gatewayConfig = super.get(key);
        return reload(gatewayConfig, gatewayConfig != null, key, Function.identity());
    }

    @Override
    public List<T> multiGet(Collection<? extends String> keys) {
        List<T> list = super.multiGet(keys);
        if (list.size() < keys.size()) {
            keys.removeAll(list.stream().map(t -> String.valueOf(ReflectUtil.getPropertyValue(t, keyField())))
                    .collect(Collectors.toList()));
            List<T> mongoList = find(Query.query(Criteria.where(keyField()).in(keys)));
            if (CollectionUtils.isNotEmpty(mongoList)) {
                list.addAll(mongoList);
                super.putAll(mongoList.stream().collect(Collectors
                        .toMap(t -> String.valueOf(ReflectUtil.getPropertyValue(t, keyField())), Function.identity())));
            }
        }
        return list;
    }

    @Override
    public void put(String key, T value) {
        save(value);
        super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends T> map) {
        map.forEach((k, v) -> save(v));
        super.putAll(map);
    }

    @Override
    public long delete(String key) {
        remove(Query.query(Criteria.where(keyField()).is(key))).getDeletedCount();
        return super.delete(key);
    }

    @Override
    public long delete(Collection<? extends String> keys) {
        remove(Query.query(Criteria.where(keyField()).in(keys))).getDeletedCount();
        return super.delete(keys);
    }

    /**
     * 获取关键信息字段名
     * 
     * @return 字段名
     */
    protected abstract String keyField();

    /**
     * 根据表达式重新加载业务数据
     * 
     * @param <R>        返回类型
     * @param source     源数据
     * @param expression 表达式
     * @param key        关键信息
     * @param function   转换函数
     * @return 返回结果
     */
    protected <R> R reload(R source, boolean expression, String key, Function<T, R> function) {
        if (expression) {
            return source;
        }
        T gatewayConfig = findOne(Query.query(Criteria.where(keyField()).is(key)));
        if (gatewayConfig != null) {
            super.put(key, gatewayConfig);
        }
        return function.apply(gatewayConfig);
    }

}
