package per.cby.frame.redis.storage.set;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Redis集合存储接口抽象实现
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@SuppressWarnings("unchecked")
public class FlexRedisSetStorageImpl<T> extends AbstractRedisSetStorage<T> implements FlexRedisSetStorage<T> {

    /**
     * 初始化Redis容器组件
     */
    public FlexRedisSetStorageImpl() {
        super(null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public FlexRedisSetStorageImpl(String name, Class<T> bizClass) {
        super(name, bizClass);
    }

    @Override
    public Set<T> difference(String key, String otherKey) {
        return super.difference(key(key), otherKey);
    }

    @Override
    public Set<T> difference(String key, Collection<String> otherKeys) {
        return super.difference(key(key), otherKeys);
    }

    @Override
    public long differenceAndStore(String key, String otherKey, String destKey) {
        return super.differenceAndStore(key(key), otherKey, destKey);
    }

    @Override
    public long differenceAndStore(String key, Collection<String> otherKeys, String destKey) {
        return super.differenceAndStore(key(key), otherKeys, destKey);
    }

    @Override
    public Set<T> intersect(String key, String otherKey) {
        return super.intersect(key(key), otherKey);
    }

    @Override
    public Set<T> intersect(String key, Collection<String> otherKeys) {
        return super.intersect(key(key), otherKeys);
    }

    @Override
    public long intersectAndStore(String key, String otherKey, String destKey) {
        return super.intersectAndStore(key(key), otherKey, destKey);
    }

    @Override
    public long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return super.intersectAndStore(key(key), otherKeys, destKey);
    }

    @Override
    public Set<T> union(String key, String otherKey) {
        return super.union(key(key), otherKey);
    }

    @Override
    public Set<T> union(String key, Collection<String> otherKeys) {
        return super.union(key(key), otherKeys);
    }

    @Override
    public long unionAndStore(String key, String otherKey, String destKey) {
        return super.unionAndStore(key(key), otherKey, destKey);
    }

    @Override
    public long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return super.unionAndStore(key(key), otherKeys, destKey);
    }

    @Override
    public long add(String key, T... values) {
        return super.add(key(key), values);
    }

    @Override
    public long addAll(String key, Collection<? extends T> values) {
        return add(key, (T[]) values.toArray());
    }

    @Override
    public boolean isMember(String key, T o) {
        return super.isMember(key(key), o);
    }

    @Override
    public Set<T> members(String key) {
        return super.members(key(key));
    }

    @Override
    public boolean move(String key, T value, String destKey) {
        return super.move(key(key), value, destKey);
    }

    @Override
    public T randomMember(String key) {
        return super.randomMember(key(key));
    }

    @Override
    public Set<T> distinctRandomMembers(String key, long count) {
        return super.distinctRandomMembers(key(key), count);
    }

    @Override
    public List<T> randomMembers(String key, long count) {
        return super.randomMembers(key(key), count);
    }

    @Override
    public long remove(String key, Collection<? extends T> values) {
        return super.remove(key(key), values);
    }

    @Override
    public T pop(String key) {
        return super.pop(key(key));
    }

    @Override
    public long size(String key) {
        return super.size(key(key));
    }

}
