package per.cby.frame.redis.strategy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import per.cby.frame.common.base.BaseService;
import per.cby.frame.common.util.LambdaUtil;
import per.cby.frame.redis.storage.hash.DefaultRedisHashStorage;

/**
 * Redis和数据存储的策略服务
 * 
 * @author chenboyang
 * @since 2019年12月13日
 *
 * @param <T>
 */
public abstract class RedisBizStrategy<T> extends DefaultRedisHashStorage<String, T> {

    @Autowired
    protected BaseService<T> baseService;

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
            keys.removeAll(list.stream().map(t -> LambdaUtil.funcToName(keyColumn())).collect(Collectors.toList()));
            List<T> bizList = baseService.lambdaQuery().in(keyColumn(), keys).list();
            if (CollectionUtils.isNotEmpty(bizList)) {
                list.addAll(bizList);
                super.putAll(bizList.stream()
                        .collect(Collectors.toMap(t -> LambdaUtil.funcToName(keyColumn()), Function.identity())));
            }
        }
        return list;
    }

    @Override
    public void put(String key, T value) {
        baseService.saveOrUpdate(value);
        super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends T> map) {
        map.forEach((k, v) -> baseService.save(v));
        super.putAll(map);
    }

    @Override
    public long delete(String key) {
        baseService.lambdaUpdate().eq(keyColumn(), key).remove();
        return super.delete(key);
    }

    @Override
    public long delete(Collection<? extends String> keys) {
        baseService.lambdaUpdate().in(keyColumn(), keys).remove();
        return super.delete(keys);
    }

    /**
     * 获取关键信息字段列
     * 
     * @return 字段列
     */
    protected abstract SFunction<T, ?> keyColumn();

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
        T gatewayConfig = baseService.lambdaQuery().eq(keyColumn(), key).one();
        if (gatewayConfig != null) {
            super.put(key, gatewayConfig);
        }
        return function.apply(gatewayConfig);
    }

}
