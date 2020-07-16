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
public class FlexRedisListStorageImpl<T> extends AbstractRedisListStorage<T> implements FlexRedisListStorage<T> {

    /**
     * 初始化Redis容器组件
     */
    public FlexRedisListStorageImpl() {
        super(null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public FlexRedisListStorageImpl(String name, Class<T> bizClass) {
        super(name, bizClass);
    }

    @Override
    public List<T> range(String key, long start, long end) {
        return super.range(key(key), start, end);
    }

    @Override
    public void trim(String key, long start, long end) {
        super.trim(key(key), start, end);
    }

    @Override
    public long size(String key) {
        return super.size(key(key));
    }

    @Override
    public long leftPush(String key, T value) {
        return super.leftPush(key(key), value);
    }

    @Override
    public long leftPushAll(String key, T... values) {
        return super.leftPushAll(key(key), values);
    }

    @Override
    public long leftPushAll(String key, Collection<? extends T> values) {
        return super.leftPushAll(key(key), values);
    }

    @Override
    public long leftPushIfPresent(String key, T value) {
        return super.leftPushIfPresent(key(key), value);
    }

    @Override
    public long leftPush(String key, T pivot, T value) {
        return super.leftPush(key(key), pivot, value);
    }

    @Override
    public long rightPush(String key, T value) {
        return super.rightPush(key(key), value);
    }

    @Override
    public long rightPushAll(String key, T... values) {
        return super.rightPushAll(key(key), values);
    }

    @Override
    public long rightPushAll(String key, Collection<? extends T> values) {
        return super.rightPushAll(key(key), values);
    }

    @Override
    public long rightPushIfPresent(String key, T value) {
        return super.rightPushIfPresent(key(key), value);
    }

    @Override
    public long rightPush(String key, T pivot, T value) {
        return super.rightPush(key(key), pivot, value);
    }

    @Override
    public void set(String key, long index, T value) {
        super.set(key(key), index, value);
    }

    @Override
    public long remove(String key, long count, T value) {
        return super.remove(key(key), count, value);
    }

    @Override
    public T index(String key, long index) {
        return super.index(key(key), index);
    }

    @Override
    public T leftPop(String key) {
        return super.leftPop(key(key));
    }

    @Override
    public T leftPop(String key, long timeout, TimeUnit unit) {
        return super.leftPop(key(key), timeout, unit);
    }

    @Override
    public T rightPop(String key) {
        return super.rightPop(key(key));
    }

    @Override
    public T rightPop(String key, long timeout, TimeUnit unit) {
        return super.rightPop(key(key), timeout, unit);
    }

    @Override
    public T rightPopAndLeftPush(String key, String destinationKey) {
        return super.rightPopAndLeftPush(key(key), destinationKey);
    }

    @Override
    public T rightPopAndLeftPush(String key, String destinationKey, long timeout, TimeUnit unit) {
        return super.rightPopAndLeftPush(key(key), destinationKey, timeout, unit);
    }

    @Override
    public List<T> list(String key) {
        return range(key, 0, -1);
    }

}
