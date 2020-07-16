package per.cby.frame.redis.storage.list;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis列表存储接口抽象实现
 * 
 * @author chenboyang
 * 
 * @param <T> 业务类型
 */
@SuppressWarnings("unchecked")
public class DefaultRedisListStorage<T> extends AbstractRedisListStorage<T> implements RedisListStorage<T> {

    /**
     * 初始化Redis容器组件
     */
    public DefaultRedisListStorage() {
        super(null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public DefaultRedisListStorage(String name, Class<T> bizClass) {
        super(name, bizClass);
    }

    @Override
    public List<T> range(long start, long end) {
        return super.range(key(), start, end);
    }

    @Override
    public void trim(long start, long end) {
        super.trim(key(), start, end);
    }

    @Override
    public long size() {
        return super.size(key());
    }

    @Override
    public long leftPush(T value) {
        return super.leftPush(key(), value);
    }

    @Override
    public long leftPushAll(T... values) {
        return super.leftPushAll(key(), values);
    }

    @Override
    public long leftPushAll(Collection<? extends T> values) {
        return super.leftPushAll(key(), values);
    }

    @Override
    public long leftPushIfPresent(T value) {
        return super.leftPushIfPresent(key(), value);
    }

    @Override
    public long leftPush(T pivot, T value) {
        return super.leftPush(key(), pivot, value);
    }

    @Override
    public long rightPush(T value) {
        return super.rightPush(key(), value);
    }

    @Override
    public long rightPushAll(T... values) {
        return super.rightPushAll(key(), values);
    }

    @Override
    public long rightPushAll(Collection<? extends T> values) {
        return super.rightPushAll(key(), values);
    }

    @Override
    public long rightPushIfPresent(T value) {
        return super.rightPushIfPresent(key(), value);
    }

    @Override
    public long rightPush(T pivot, T value) {
        return super.rightPush(key(), pivot, value);
    }

    @Override
    public void set(long index, T value) {
        super.set(key(), index, value);
    }

    @Override
    public long remove(long count, T value) {
        return super.remove(key(), count, value);
    }

    @Override
    public T index(long index) {
        return super.index(key(), index);
    }

    @Override
    public T leftPop() {
        return super.leftPop(key());
    }

    @Override
    public T leftPop(long timeout, TimeUnit unit) {
        return super.leftPop(key(), timeout, unit);
    }

    @Override
    public T rightPop() {
        return super.rightPop(key());
    }

    @Override
    public T rightPop(long timeout, TimeUnit unit) {
        return super.rightPop(key(), timeout, unit);
    }

    @Override
    public T rightPopAndLeftPush(String destinationKey) {
        return super.rightPopAndLeftPush(key(), destinationKey);
    }

    @Override
    public T rightPopAndLeftPush(String destinationKey, long timeout, TimeUnit unit) {
        return super.rightPopAndLeftPush(key(), destinationKey, timeout, unit);
    }

    @Override
    public List<T> list() {
        return super.list(key());
    }

}
