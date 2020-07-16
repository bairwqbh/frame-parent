package per.cby.frame.redis.storage.list;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.support.collections.RedisCollectionFactoryBean.CollectionType;

import per.cby.frame.redis.storage.AbstractRedisContainerStorage;

/**
 * Redis列表存储接口抽象实现
 * 
 * @author chenboyang
 * 
 * @param <T> 业务类型
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRedisListStorage<T> extends AbstractRedisContainerStorage<String, T>
        implements FlexRedisListStorage<T> {

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public AbstractRedisListStorage(String name, Class<T> bizClass) {
        super(name, String.class, bizClass);
    }

    /**
     * 获取列表操作器
     * 
     * @return 哈希操作器
     */
    public ListOperations<String, String> ops() {
        return template().opsForList();
    }

    @Override
    public List<T> range(String key, long start, long end) {
        return valueDeserialize(ops().range(key, start, end), CollectionType.LIST);
    }

    @Override
    public void trim(String key, long start, long end) {
        ops().trim(key, start, end);
    }

    @Override
    public long size(String key) {
        return Optional.ofNullable(ops().size(key)).orElse(0L);
    }

    @Override
    public long leftPush(String key, T value) {
        return Optional.ofNullable(ops().leftPush(key, valueSerialize(value))).orElse(0L);
    }

    @Override
    public long leftPushAll(String key, T... values) {
        return Optional.ofNullable(ops().leftPushAll(key, valueSerialize(values))).orElse(0L);
    }

    @Override
    public long leftPushAll(String key, Collection<? extends T> values) {
        return Optional.ofNullable(ops().leftPushAll(key, valueSerialize(values, CollectionType.LIST))).orElse(0L);
    }

    @Override
    public long leftPushIfPresent(String key, T value) {
        return Optional.ofNullable(ops().leftPushIfPresent(key, valueSerialize(value))).orElse(0L);
    }

    @Override
    public long leftPush(String key, T pivot, T value) {
        return Optional.ofNullable(ops().leftPush(key, valueSerialize(pivot), valueSerialize(value))).orElse(0L);
    }

    @Override
    public long rightPush(String key, T value) {
        return Optional.ofNullable(ops().rightPush(key, valueSerialize(value))).orElse(0L);
    }

    @Override
    public long rightPushAll(String key, T... values) {
        return Optional.ofNullable(ops().rightPushAll(key, valueSerialize(values))).orElse(0L);
    }

    @Override
    public long rightPushAll(String key, Collection<? extends T> values) {
        return Optional.ofNullable(ops().rightPushAll(key, valueSerialize(values, CollectionType.LIST))).orElse(0L);
    }

    @Override
    public long rightPushIfPresent(String key, T value) {
        return Optional.ofNullable(ops().rightPushIfPresent(key, valueSerialize(value))).orElse(0L);
    }

    @Override
    public long rightPush(String key, T pivot, T value) {
        return Optional.ofNullable(ops().rightPush(key, valueSerialize(pivot), valueSerialize(value))).orElse(0L);
    }

    @Override
    public void set(String key, long index, T value) {
        ops().set(key, index, valueSerialize(value));
    }

    @Override
    public long remove(String key, long count, T value) {
        return Optional.ofNullable(ops().remove(key, count, valueSerialize(value))).orElse(0L);
    }

    @Override
    public T index(String key, long index) {
        return valueDeserialize(ops().index(key, index));
    }

    @Override
    public T leftPop(String key) {
        return valueDeserialize(ops().leftPop(key));
    }

    @Override
    public T leftPop(String key, long timeout, TimeUnit unit) {
        return valueDeserialize(ops().leftPop(key, timeout, unit));
    }

    @Override
    public T rightPop(String key) {
        return valueDeserialize(ops().rightPop(key));
    }

    @Override
    public T rightPop(String key, long timeout, TimeUnit unit) {
        return valueDeserialize(ops().rightPop(key, timeout, unit));
    }

    @Override
    public T rightPopAndLeftPush(String key, String destinationKey) {
        return valueDeserialize(ops().rightPopAndLeftPush(key, destinationKey));
    }

    @Override
    public T rightPopAndLeftPush(String key, String destinationKey, long timeout, TimeUnit unit) {
        return valueDeserialize(ops().rightPopAndLeftPush(key, destinationKey, timeout, unit));
    }

    @Override
    public List<T> list(String key) {
        return range(key, 0, -1);
    }

}
